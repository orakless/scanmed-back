package sae.scanmedback.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @Column(unique = true, nullable = false)
    @SequenceGenerator(name = "utilisateur_seq", sequenceName = "utilisateur_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_seq")
    private Integer id;

    @Column(name = "pseudonyme")
    private String pseudonyme;

    @Column(name = "mail", unique = true)
    private String mail;

    @Column(name = "est_admin")
    boolean estAdmin;

    @Column(name = "accepte_mails")
    boolean accepteMails;

    @Column(name = "mot_de_passe")
    String motDePasse;

    public Utilisateur() {};

    public Integer getId() { return this.id; }

    public String getPseudonyme() { return this.pseudonyme; }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }
    public boolean isAccepteMails() {
        return accepteMails;
    }

    public void setAccepteMails(boolean accepteMails) {
        this.accepteMails = accepteMails;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public String getMail() { return this.mail; }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean estAdmin() { return this.estAdmin; }
}
