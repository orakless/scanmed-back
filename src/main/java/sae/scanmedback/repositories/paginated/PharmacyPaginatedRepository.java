package sae.scanmedback.repositories.paginated;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.City;
import sae.scanmedback.entities.Pharmacy;

import java.util.List;

@Repository
public interface PharmacyPaginatedRepository extends ListPagingAndSortingRepository<Pharmacy, Integer> {
    Page<Pharmacy> findAllByCity(City city, Pageable pageable);
}
