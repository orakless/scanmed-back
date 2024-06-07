package sae.scanmedback.controllers;


import jakarta.persistence.Index;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.NewReportDTO;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.data.PageData;
import sae.scanmedback.api.response.data.ReportStateChangeDTO;
import sae.scanmedback.api.response.data.ResponseReportDTO;
import sae.scanmedback.entities.*;
import sae.scanmedback.services.IDataService;
import sae.scanmedback.services.IReportService;
import sae.scanmedback.services.IUserService;
import sae.scanmedback.utilities.ConversionUtilities;

import javax.print.attribute.standard.Media;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final IUserService userService;
    private final IDataService dataService;
    private final IReportService reportService;
    public ReportController(IUserService userService,
                            IDataService dataService,
                            IReportService reportService) {
        this.dataService = dataService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> getAllReports(@RequestParam int page) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
            Page<Report> reports = reportService.getAllReportsFrom(user, page);
            PageData<ResponseReportDTO> data = ConversionUtilities.getReportPageData(reports, reportService);
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    data,
                    null), HttpStatus.OK);

        } catch(IndexOutOfBoundsException | UsernameNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "{id}/history",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> getReportStateHistory(@PathVariable(name = "id") int reportId,
                                                           @RequestParam int page) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
            Report report = reportService.getReport(reportId, user);
            Page<ReportStateChange> reportStateChanges = reportService.getReportHistory(report, page);
            PageData<ReportStateChangeDTO> data = ConversionUtilities.getReportSCPageData(reportStateChanges);
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    data,
                    null), HttpStatus.OK
            );
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IndexOutOfBoundsException | UsernameNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/new",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> newReport(@RequestBody NewReportDTO infos) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
            Pharmacy pharmacy = dataService.getPharmacyFromId(infos.getPharmacyId());
            Medecine medecine = dataService.getMedecineFromCIP(infos.getMedecineCIP());
            reportService.addReport(user, pharmacy, medecine);
            return new ResponseEntity<>(new ValidResponse("success",
                    null,
                    "Report successfully created."), HttpStatus.CREATED);
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
