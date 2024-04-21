package sae.scanmedback.entities;

import java.io.Serializable;

public class JetonId implements Serializable {
    private String jeton;
    private Utilisateur utilisateur;

    public JetonId(String jeton, Utilisateur utilisateur) {
        this.jeton = jeton;
        this.utilisateur = utilisateur;
    }
}
