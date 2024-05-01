package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.DeviceAlreadyAuthenticatedException;
import sae.scanmedback.repositories.TokenRepository;

@Service
@Transactional
public class AuthService implements IAuthService {
    private final TokenRepository tokenRepository;

    public AuthService(final TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token generateToken(User user, String device) throws DeviceAlreadyAuthenticatedException {
        if (tokenRepository.existsTokenByUserAndDevice(user, device))
            throw new DeviceAlreadyAuthenticatedException("JAA.001;This device name is already used for this user.");

        Token token = new Token();
        token.setUser(user);
        token.setDevice(device);

        while (tokenRepository.existsTokenByUserAndToken(user, token.getToken())) {
            token.rerollToken();
        }
        return tokenRepository.save(token);
    }

    @Override
    public boolean checkToken(User user, String token) {
        return tokenRepository.existsTokenByUserAndToken(user, token);
    }
}
