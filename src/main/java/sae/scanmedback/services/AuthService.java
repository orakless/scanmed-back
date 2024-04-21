package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.Jeton;
import sae.scanmedback.entities.JetonId;
import sae.scanmedback.entities.Utilisateur;
import sae.scanmedback.errors.AppareilAlreadyAuthenticatedException;
import sae.scanmedback.repositories.JetonRepository;
import sae.scanmedback.security.JetonsUtilities;

@Service
@Transactional
public class AuthService implements IAuthService {
    @Autowired
    JetonRepository jetonRepository;

    @Override
    public Jeton generateJeton(Utilisateur utilisateur, String nomAppareil) throws AppareilAlreadyAuthenticatedException {
        if (jetonRepository.existsJetonByUtilisateurAndNomAppareil(utilisateur, nomAppareil))
            throw new AppareilAlreadyAuthenticatedException("JAA.001;This device name is already used for this user.");

        Jeton jeton = new Jeton();
        jeton.setUtilisateur(utilisateur);
        jeton.setNomAppareil(nomAppareil);

        while (jetonRepository.existsJetonByUtilisateurAndJeton(utilisateur, jeton.getJeton())) {
            jeton.rerollJeton();
        }
        return jetonRepository.save(jeton);
    }

    @Override
    public boolean checkJeton(Utilisateur utilisateur, String jeton) {
        return jetonRepository.existsJetonByUtilisateurAndJeton(utilisateur, jeton);
    }
}
