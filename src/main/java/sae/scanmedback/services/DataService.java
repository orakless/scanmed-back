package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.City;
import sae.scanmedback.entities.Pharmacy;
import sae.scanmedback.repositories.CityRepository;
import sae.scanmedback.repositories.paginated.CityPaginatedRepository;
import sae.scanmedback.repositories.paginated.PharmacyPaginatedRepository;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Transactional
public class DataService implements IDataService {
    private final CityPaginatedRepository cityPaginatedRepository;
    private final CityRepository cityRepository;
    private final PharmacyPaginatedRepository pharmacyPaginatedRepository;

    public DataService(CityPaginatedRepository cityPaginatedRepository,
                       PharmacyPaginatedRepository pharmacyPaginatedRepository,
                       CityRepository cityRepository) {
        this.cityPaginatedRepository = cityPaginatedRepository;
        this.cityRepository = cityRepository;
        this.pharmacyPaginatedRepository = pharmacyPaginatedRepository;
    }

    @Override
    public Page<City> getAllCities(int page, String sort, String order) throws IndexOutOfBoundsException {
        if (page < 0) {
            throw new IndexOutOfBoundsException("PIN;Page index negative");
        }
        return cityPaginatedRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public Page<Pharmacy> getAllPharmaciesFromCity(int page, int cityId)
            throws IndexOutOfBoundsException, NoSuchElementException {
        if (page < 0) {
            throw new IndexOutOfBoundsException("PIN;Page index negative");
        }
        Optional<City> city = cityRepository.findById(cityId);

        if (city.isEmpty()) {
            throw new NoSuchElementException("INF;Could not find the city.");
        }
        return pharmacyPaginatedRepository.findAllByCity(city.get(), PageRequest.of(page, 5));
    }
}
