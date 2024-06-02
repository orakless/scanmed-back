package sae.scanmedback.services;

import org.springframework.stereotype.Service;
import sae.scanmedback.utilities.TokenUtilities;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class PasswordResetService implements IPasswordResetService {
    private class PasswordResetToken {
        private static final int TIME_LIMIT = 1000 * 60 * 15; // 15 minutes

        String token;
        Date expiration;

        public PasswordResetToken(String token) {
            this.token = token;
            this.expiration = new Date(new Date().getTime() + TIME_LIMIT);
        }

        public boolean isExpired() {
            return expiration.before(new Date());
        }

        public boolean checkToken(String token) {
            return this.token.equals(token);
        }
    }

    ConcurrentMap<String, PasswordResetToken> tokens = new ConcurrentHashMap<>();

    @Override
    public String generateToken(String email) {
        String token = TokenUtilities.generatePasswordResetToken();
        this.tokens.put(email, new PasswordResetToken(token));
        return token;
    }

    @Override
    public boolean verifyToken(String email, String token) {
        PasswordResetToken passwordResetToken = this.tokens.getOrDefault(email, null);

        if (passwordResetToken == null) return false;

        return (passwordResetToken.checkToken(token) && !passwordResetToken.isExpired());
    }
}
