package sae.scanmedback.entities;

import jakarta.persistence.*;
import sae.scanmedback.utilities.TokenUtilities;

@Entity
@IdClass(TokenId.class)
@Table(name = "Tokens")
public class Token {
    public String getToken() {
        return token;
    }

    @Id
    @Column(name = "token")
    private String token;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDevice() {
        return device;
    }

    public void rerollToken() {
        this.token = TokenUtilities.generateToken();
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Column(name = "device")
    String device;

    public Token() { rerollToken(); }
}
