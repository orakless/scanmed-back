package sae.scanmedback.services;

public interface IPasswordResetService {
    String generateToken(String email);

    boolean verifyToken(String email, String token);
}
