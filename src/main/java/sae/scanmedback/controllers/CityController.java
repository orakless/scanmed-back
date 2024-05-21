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
import sae.scanmedback.entities.City;
import sae.scanmedback.entities.Pharmacy;
import sae.scanmedback.services.IDataService;

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
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    cities,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "{id}/pharmacies")
    public ResponseEntity<IResponse> getAllPharmaciesFromCity(@PathVariable(name = "id") int cityId,
                                                              @RequestParam int page) {
        try {
            Page<Pharmacy> pharmacies = dataService.getAllPharmaciesFromCity(page, cityId);
            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    pharmacies,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
