package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Medecine;

@Repository
public interface MedecineRepository extends JpaRepository<Medecine, Integer> { }
