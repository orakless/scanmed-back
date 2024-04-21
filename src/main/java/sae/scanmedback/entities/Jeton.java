package sae.scanmedback.entities;

import jakarta.persistence.*;
import sae.scanmedback.security.JetonsUtilities;

@Entity
@IdClass(JetonId.class)
@Table(name = "jetons")
public class Jeton {
    public String getJeton() {
        return jeton;
    }

    @Id
    @Column(name = "jeton")
    private String jeton;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    Utilisateur utilisateur;
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getNomAppareil() {
        return nomAppareil;
    }

    public void rerollJeton() {
        this.jeton = JetonsUtilities.generateToken();
    }

    public void setNomAppareil(String nomAppareil) {
        this.nomAppareil = nomAppareil;
    }

    @Column(name = "nom_appareil")
    String nomAppareil;

    public Jeton() { rerollJeton(); }
}
