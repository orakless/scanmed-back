package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.ReportStateChange;

import java.util.Optional;

@Repository
public interface ReportStateChangeRepository extends JpaRepository<ReportStateChange, Integer> {
    Optional<ReportStateChange> findTopByReportOrderByActionDateDesc(Report report);
}
