package sae.scanmedback.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.entities.Medecine;
import sae.scanmedback.services.IDataService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/medecine")
public class MedecineController {
    private final IDataService dataService;

    public MedecineController(final IDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<IResponse> getCity(@PathVariable(name = "id") String CIP) {
        try {
            Medecine medecine = dataService.getMedecineFromCIP(CIP);
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    medecine,
                    null
            ), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
