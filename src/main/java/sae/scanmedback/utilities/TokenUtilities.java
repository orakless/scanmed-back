package sae.scanmedback.utilities;


import java.security.SecureRandom;
import java.util.Base64;

public class TokenUtilities {
    private static final int TOKEN_LENGTH = 64; // must be divisible by 4
    private static final int PASSWORD_RESET_TOKEN_LENGTH = 6;

    private static final SecureRandom randomGenerator = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    public static String generateToken() {
        byte[] randomBytes = new byte[(TOKEN_LENGTH / 4) * 3];
        // Generate the random bytes
        randomGenerator.nextBytes(randomBytes);
        // Converts it in a better manner for HTTP requests
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String generatePasswordResetToken() {
        byte[] randomBytes = new byte[PASSWORD_RESET_TOKEN_LENGTH];
        randomGenerator.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
