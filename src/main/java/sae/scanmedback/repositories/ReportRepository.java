package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
