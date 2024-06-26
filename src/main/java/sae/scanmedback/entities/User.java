package sae.scanmedback.entities;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @Column(unique = true, nullable = false)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    public boolean isAdmin() {
        return isAdmin;
    }

    @Column(name = "is_admin")
    private boolean isAdmin;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setAcceptsEmails(boolean acceptsEmails) {
        this.acceptsEmails = acceptsEmails;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean acceptsEmails() {
        return acceptsEmails;
    }

    @Column(name = "accepts_emails")
    private boolean acceptsEmails;

    @Column(name = "password")
    private String password;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    @Column(name = "avatar_id", nullable = true)
    private int avatar;

    public User() {};

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getDisplayName() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
