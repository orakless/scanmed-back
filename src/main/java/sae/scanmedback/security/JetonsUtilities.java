package sae.scanmedback.security;


import java.security.SecureRandom;
import java.util.Base64;

public class JetonsUtilities {
    private static final int TOKEN_LENGTH = 64; // must be divisible by 4
    private static final SecureRandom randomGenerator = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    public static String generateToken() {
        byte[] randomBytes = new byte[(TOKEN_LENGTH / 4) * 3];
        // Generate the random bytes
        randomGenerator.nextBytes(randomBytes);
        // Converts it in a better manner for HTTP requests
        return base64Encoder.encodeToString(randomBytes);
    }
}
