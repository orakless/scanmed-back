package sae.scanmedback.entities;

import jakarta.persistence.*;

@Entity
@IdClass(JetonId.class)
@Table(name = "jeton")
public class Jeton {
    public String getJeton() {
        return jeton;
    }

    public void setJeton(String jeton) {
        this.jeton = jeton;
    }

    @Id
    private String jeton;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    Utilisateur utilisateur;

    public String getNomAppareil() {
        return nomAppareil;
    }

    public void setNomAppareil(String nomAppareil) {
        this.nomAppareil = nomAppareil;
    }

    @Column(name = "nom_appareil")
    String nomAppareil;

    public Jeton() {
    }
}
