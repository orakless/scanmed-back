package sae.scanmedback.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Pharmacies")
public class Pharmacy {
    @Id
    @Column(unique = true, nullable = false)
    @SequenceGenerator(name = "pharmacy_seq", sequenceName = "pharmacy_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pharmacy_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    City city;

    @Column(name = "number")
    private String number;

    @Column(name = "street")
    private String street;

    public Pharmacy() {}

    public int getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

}
