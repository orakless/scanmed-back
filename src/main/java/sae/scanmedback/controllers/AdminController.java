package sae.scanmedback.controllers;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.StateChangeDTO;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.data.PageData;
import sae.scanmedback.api.response.data.ResponseReportDTO;
import sae.scanmedback.entities.Pharmacy;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.ReportStateChange;
import sae.scanmedback.entities.User;
import sae.scanmedback.services.IDataService;
import sae.scanmedback.services.IMailService;
import sae.scanmedback.services.IReportService;
import sae.scanmedback.services.IUserService;
import sae.scanmedback.utilities.ConversionUtilities;
import sae.scanmedback.utilities.TranslationUtilities;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final IUserService userService;
    private final IReportService reportService;
    private final IDataService dataService;

    private final IMailService mailService;
    public AdminController(IUserService userService, IReportService reportService, IDataService dataService,
                           IMailService mailService) {
        this.userService = userService;
        this.reportService = reportService;
        this.dataService = dataService;
        this.mailService = mailService;
    }

    @GetMapping(path = "pharmacy/{pharmacyId}/reports",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> getAllReportsFromPharmacy(@PathVariable int pharmacyId, @RequestParam int page)
    {
        Authentication userAuth = SecurityContextHolder.getContext()
                .getAuthentication();
        try {
            User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
            if (!user.isAdmin())
                return new ResponseEntity<>(
                        new ErrorResponse("NER;You do not have enough rights for this."),
                        HttpStatus.UNAUTHORIZED
                );
            Pharmacy pharmacy = dataService.getPharmacyFromId(pharmacyId);
            PageData<ResponseReportDTO> data = ConversionUtilities.getReportPageData(
                    reportService.getAllReportsFromPharmacy(pharmacy, page),
                    reportService
            );
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    data,
                    null), HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "report/{id}/change_state",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> changeReportState(@PathVariable int id, @RequestBody StateChangeDTO infos) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
            if (!user.isAdmin())
                return new ResponseEntity<>(
                        new ErrorResponse("NER;You do not have enough rights for this."),
                        HttpStatus.UNAUTHORIZED
                );
            Report report = reportService.getReportById(id);
            reportService.changeState(report, infos.getNewState());

            try {
                User reportUser = report.getUser();
                if (reportUser.acceptsEmails()) {
                    ReportStateChange lastState = reportService.getLastChange(report);
                    mailService.sendStateChangeMail(reportUser, report, lastState);
                }
            } catch (MailException | MessagingException ex) {
                System.err.println(ex.getMessage());
            }
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    null,
                    "Report state added"
            ), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
