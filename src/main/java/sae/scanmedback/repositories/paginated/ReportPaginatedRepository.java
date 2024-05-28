package sae.scanmedback.repositories.paginated;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.City;
import sae.scanmedback.entities.Pharmacy;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.User;

@Repository
public interface ReportPaginatedRepository extends ListPagingAndSortingRepository<Report, Integer> {
    Page<Report> findAllByUser(User user, Pageable pageable);
}
