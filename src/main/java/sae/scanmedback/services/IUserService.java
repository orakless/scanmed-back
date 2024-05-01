package sae.scanmedback.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sae.scanmedback.api.dto.RegisterDTO;
import sae.scanmedback.entities.User;

public interface IUserService {
    User registerNewUser(RegisterDTO infos);

    User loadUserByEmail(String email);
    User login(String email, String password);
}
