package sae.scanmedback.security;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import sae.scanmedback.errors.InvalidPasswordException;

import java.util.regex.Pattern;

public class PasswordUtilities {
    private static final String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,64}$";
    private static final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public static String hashPassword(String password) {
        System.out.println(encoder.encode(password).length());
        return encoder.encode(password);
    }

    public static String validatePassword(String password) throws InvalidPasswordException {
        if (!pattern.matcher(password).matches())
            throw new InvalidPasswordException("PNV;Password does not meet requirements");
        return password;
    }

    public static boolean checkPassword(String expected, String current) {
        return encoder.matches(current, expected);
    }
}
