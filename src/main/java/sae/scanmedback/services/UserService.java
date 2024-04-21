package sae.scanmedback.services;

import com.sun.tools.jconsole.JConsoleContext;
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

    @Override
    public Utilisateur login(String mail, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByMail(mail);

        if (utilisateur == null || !PasswordUtilities.checkPassword(utilisateur.getMotDePasse(), motDePasse)) {
            return null;
        }
        return utilisateur;
    }
}
