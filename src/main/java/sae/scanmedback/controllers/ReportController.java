package sae.scanmedback.controllers;


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
import sae.scanmedback.entities.Medecine;
import sae.scanmedback.entities.Pharmacy;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.User;
import sae.scanmedback.services.IDataService;
import sae.scanmedback.services.IReportService;
import sae.scanmedback.services.IUserService;

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
            PageData<Report> data = new PageData<>(
                    reports.getContent(),
                    reports.getTotalPages(),
                    reports.getPageable().getPageNumber()
            );
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    data,
                    null), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/new",
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
        } catch (NoSuchElementException | UsernameNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
