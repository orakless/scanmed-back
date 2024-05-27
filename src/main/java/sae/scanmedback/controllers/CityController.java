package sae.scanmedback.controllers;


import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.data.PageData;
import sae.scanmedback.entities.City;
import sae.scanmedback.entities.Pharmacy;
import sae.scanmedback.services.IDataService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/city")
public class CityController {
    private final IDataService dataService;

    public CityController(final IDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> getAllCities(@RequestParam int page,
                                                  @RequestParam(defaultValue = "name") String sort,
                                                  @RequestParam(defaultValue = "desc") String order) {
        try {
            Page<City> cities = dataService.getAllCities(page, sort, order);
            PageData<City> data = new PageData<>(cities);
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    data,
                    null), HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}/pharmacies")
    public ResponseEntity<IResponse> getAllPharmaciesFromCity(@PathVariable(name = "id") int cityId,
                                                              @RequestParam int page) {
        try {
            Page<Pharmacy> pharmacies = dataService.getAllPharmaciesFromCity(page, cityId);
            PageData<Pharmacy> data = new PageData<>(pharmacies);
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    data,
                    null), HttpStatus.OK);
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;We could not process your request."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
