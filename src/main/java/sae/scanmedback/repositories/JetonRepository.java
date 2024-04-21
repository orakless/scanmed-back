package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sae.scanmedback.entities.Jeton;
import sae.scanmedback.entities.JetonId;
import sae.scanmedback.entities.Utilisateur;

public interface JetonRepository extends JpaRepository<Jeton, Integer>{
    boolean existsJetonByUtilisateurAndJeton(Utilisateur utilisateur, String jeton);
    boolean existsJetonByUtilisateurAndNomAppareil(Utilisateur utilisateur, String nomAppareil);
}
