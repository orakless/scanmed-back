package sae.scanmedback.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Cities")
public class City {
    @Id
    @Column(unique = true, nullable = false)
    @SequenceGenerator(name = "city_seq", sequenceName = "city_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "zip_code")
    private String zipCode;

    public int getId() { return this.id; }

    public String getNom() { return this.name; }

    public String getCodePostal() { return this.zipCode; }
}
