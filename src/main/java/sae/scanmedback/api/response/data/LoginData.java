package sae.scanmedback.api.response.data;

public class LoginData {
    private String token;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public LoginData(String token) {
        this.token = token;
    }
}
