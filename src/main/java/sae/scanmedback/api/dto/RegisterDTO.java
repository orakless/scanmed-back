package sae.scanmedback.api.dto;

public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private boolean acceptsemails;

    public RegisterDTO() {
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean getAcceptsEmails() {
        return this.acceptsemails;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public void setAcceptsEmails(boolean acceptsEmails) {
        this.acceptsemails = acceptsemails;
    }
}
