package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Medecine;

import java.util.Optional;

@Repository
public interface MedecineRepository extends JpaRepository<Medecine, Integer> {
    Optional<Medecine> findByCIP(String cip);
}
