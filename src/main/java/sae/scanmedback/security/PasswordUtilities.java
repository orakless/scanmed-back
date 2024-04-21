package sae.scanmedback.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordUtilities {
    private static final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean checkPassword(String expected, String current) {
        return encoder.matches(current, expected);
    }
}
