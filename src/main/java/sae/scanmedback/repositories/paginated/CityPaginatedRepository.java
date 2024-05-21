package sae.scanmedback.repositories.paginated;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.City;

@Repository
public interface CityPaginatedRepository extends PagingAndSortingRepository<City, Integer> {
}
