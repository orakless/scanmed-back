package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> { }
