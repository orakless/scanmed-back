package sae.scanmedback.entities;

import java.io.Serializable;

public class JetonId implements Serializable {
    private String jeton;

    public String getJeton() {
        return jeton;
    }

    public void setJeton(String jeton) {
        this.jeton = jeton;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    private Utilisateur utilisateur;

    public JetonId() {}

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof JetonId otherId)) return false;

        return otherId.utilisateur.getMail().equals(utilisateur.getMail())
                && otherId.jeton.equals(jeton);
    }

    @Override
    public int hashCode() {
        return (jeton+utilisateur.getMail()).hashCode();
    }

}
