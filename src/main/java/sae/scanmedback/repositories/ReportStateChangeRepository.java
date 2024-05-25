package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.ReportStateChange;

@Repository
public interface ReportStateChangeRepository extends JpaRepository<ReportStateChange, Integer> {
}
