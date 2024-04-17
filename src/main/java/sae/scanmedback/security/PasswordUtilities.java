package sae.scanmedback.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class PasswordUtilities {
    private static Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 1000000, 16);

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean checkPassword(String expected, String current) {
        return encoder.matches(current, expected);
    }
}
