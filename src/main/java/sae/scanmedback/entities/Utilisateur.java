package sae.scanmedback.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pseudonyme")
    private String pseudonyme;

    @Column(name = "mail")
    private String mail;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable = false)
    Ville domicile;

    @Column(name = "est_admin")
    boolean estAdmin;

    public Integer getId() { return this.id; }

    public String getPseudonyme() { return this.pseudonyme; }
    public void setPseudonyme(String pseudonyme) {
        if (pseudonyme != null && pseudonyme.length() <= 32)
            this.pseudonyme = pseudonyme;
    }

    public String getMail() { return this.mail; }

    public boolean estAdmin() { return this.estAdmin; }

    public Ville getDomicile() { return this.domicile; }
}
