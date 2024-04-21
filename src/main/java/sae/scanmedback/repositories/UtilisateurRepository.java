package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sae.scanmedback.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByMail(String mail);
}
