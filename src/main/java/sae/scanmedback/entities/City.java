package sae.scanmedback.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "zip_code")
    private String zipCode;

    public Integer getId() { return this.id; }

    public String getNom() { return this.name; }

    public String getCodePostal() { return this.zipCode; }
}
