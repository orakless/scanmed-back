package sae.scanmedback.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditDTO {
    String username;
    String password;
    String email;
    Boolean acceptsEmails;

    public EditDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAcceptsEmails() {
        return acceptsEmails;
    }

    public void setAcceptsEmails(Boolean acceptsEmails) {
        this.acceptsEmails = acceptsEmails;
    }

    public boolean isEmpty() {
        return (username == null && password == null && email == null && acceptsEmails == null);
    }
}
