package sae.scanmedback.services;

import org.springframework.data.domain.Page;
import sae.scanmedback.entities.City;
import sae.scanmedback.entities.Pharmacy;

import java.util.List;
import java.util.NoSuchElementException;

public interface IDataService {
    Page<City> getAllCities(int page, String sort, String order)
            throws IndexOutOfBoundsException;

    Page<Pharmacy> getAllPharmaciesFromCity(int page, int cityId)
            throws IndexOutOfBoundsException, NoSuchElementException;
}
