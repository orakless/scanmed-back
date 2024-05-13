package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sae.scanmedback.api.dto.RegisterDTO;
import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;
import sae.scanmedback.repositories.TokenRepository;
import sae.scanmedback.repositories.UserRepository;
import sae.scanmedback.security.PasswordUtilities;

@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    public UserService(final UserRepository userRepository, final TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User registerNewUser(RegisterDTO infos) {
        User newUser = new User();

        newUser.setUsername(infos.getUsername());
        newUser.setEmail(infos.getEmail());
        newUser.setAcceptsEmails(infos.getAcceptsEmails());
        newUser.setPassword(PasswordUtilities.hashPassword(infos.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !PasswordUtilities.checkPassword(user.getPassword(), password)) {
            return null;
        }
        return user;
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException("User with email not found.");

        return user;
    }

    public void deleteUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) throw new UsernameNotFoundException("User not found");

        tokenRepository.deleteTokensByUser(user);
        userRepository.deleteUserByEmail(email);
    }
}
