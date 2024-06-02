package sae.scanmedback.api.dto;

public class PasswordResetRequestDTO {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

}
