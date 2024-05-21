package sae.scanmedback.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Medecines")
public class Medecine {
    @Id
    @Column(name = "CIP", unique = true, nullable = false)
    private String CIP;

    @Column(name = "name")
    private String name;

    public Medecine() {}

    public String getCIP() {
        return CIP;
    }

    public String getName() {
        return name;
    }
}
