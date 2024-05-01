package sae.scanmedback.entities;

import java.io.Serializable;

public class TokenId implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public TokenId() {}

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof TokenId otherId)) return false;

        return otherId.user.getUsername().equals(user.getUsername())
                && otherId.token.equals(token);
    }

    @Override
    public int hashCode() {
        return (token+user.getUsername()).hashCode();
    }

}
