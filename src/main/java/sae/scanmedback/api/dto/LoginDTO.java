package sae.scanmedback.api.dto;

public class LoginDTO {
    private String mail;
    private String motDePasse;

    private String nomAppareil;

    public LoginDTO() {};

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNomAppareil() {
        return nomAppareil;
    }

    public void setNomAppareil(String nomAppareil) {
        this.nomAppareil = nomAppareil;
    }
}


