package sae.scanmedback.repositories.paginated;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.ReportStateChange;
import sae.scanmedback.entities.User;
import sae.scanmedback.entities.subtypes.ReportState;

@Repository
public interface ReportStateChangePaginatedRepository extends ListPagingAndSortingRepository<ReportStateChange, Integer> {
    Page<ReportStateChange> findAllByReport(Report report, Pageable pageable);
}
