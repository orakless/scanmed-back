package sae.scanmedback.services;

import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.DeviceAlreadyAuthenticatedException;

public interface IAuthService {
    Token generateToken(User user, String device) throws DeviceAlreadyAuthenticatedException;

    boolean checkToken(User user, String token);
}
