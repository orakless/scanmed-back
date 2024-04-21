package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sae.scanmedback.entities.Jeton;

public interface JetonRepository extends JpaRepository<Jeton, Integer>{
}
