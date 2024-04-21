package sae.scanmedback.services;

import sae.scanmedback.entities.Utilisateur;

public interface IUserService {
    Utilisateur registerNewUtilisateur(Utilisateur newUtilisateur);
    Utilisateur login(String mail, String motDePasse);
}
