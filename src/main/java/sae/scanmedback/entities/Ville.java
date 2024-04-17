package sae.scanmedback.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "code_postal")
    private String codePostal;

    public Integer getId() { return this.id; }

    public String getNom() { return this.nom; }

    public String getCodePostal() { return this.codePostal; }
}
