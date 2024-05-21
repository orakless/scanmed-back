package sae.scanmedback.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sae.scanmedback.services.IDataService;

@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {
    private final IDataService dataService;

    public PharmacyController(final IDataService dataService) {
        this.dataService = dataService;
    }


}
