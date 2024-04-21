package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.Utilisateur;
import sae.scanmedback.repositories.UtilisateurRepository;
import sae.scanmedback.security.PasswordUtilities;
@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur registerNewUtilisateur(Utilisateur newUtilisateur) {
        newUtilisateur.setMotDePasse(PasswordUtilities.hashPassword(newUtilisateur.getMotDePasse()));
        return utilisateurRepository.save(newUtilisateur);
    }
}
