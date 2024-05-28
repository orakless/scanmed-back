package sae.scanmedback.api.response.data;

public class UserData {
    String username;
    String email;
    Boolean acceptsEmails;
    Integer avatarId;

    public UserData(String username, String email, Boolean acceptsEmails, Integer avatarId) {
        this.username = username;
        this.email = email;
        this.acceptsEmails = acceptsEmails;
        this.avatarId = avatarId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }
}
