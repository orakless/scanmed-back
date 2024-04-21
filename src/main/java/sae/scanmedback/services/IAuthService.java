package sae.scanmedback.services;

import sae.scanmedback.entities.Jeton;
import sae.scanmedback.entities.Utilisateur;
import sae.scanmedback.errors.AppareilAlreadyAuthenticatedException;

public interface IAuthService {
    Jeton generateJeton(Utilisateur utilisateur, String nomAppareil) throws AppareilAlreadyAuthenticatedException;

    boolean checkJeton(Utilisateur utilisateur, String jeton);
}
