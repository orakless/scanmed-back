package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sae.scanmedback.api.dto.EditDTO;
import sae.scanmedback.api.dto.RegisterDTO;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.EmailAlreadyUsedException;
import sae.scanmedback.errors.EmptyDTOException;
import sae.scanmedback.errors.InvalidPasswordException;
import sae.scanmedback.repositories.TokenRepository;
import sae.scanmedback.repositories.UserRepository;
import sae.scanmedback.utilities.PasswordUtilities;

@Service
@Transactional
public class UserService implements IUserService {
    private final static int DEFAULT_AVATAR = 1;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    public UserService(final UserRepository userRepository, final TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User registerNewUser(RegisterDTO infos) throws InvalidPasswordException {
        User newUser = new User();

        newUser.setUsername(infos.getUsername());
        newUser.setEmail(infos.getEmail());
        newUser.setAcceptsEmails(infos.getAcceptsEmails());
        newUser.setPassword(
                PasswordUtilities.hashPassword(
                        PasswordUtilities.validatePassword(infos.getPassword())));
        newUser.setAvatar(DEFAULT_AVATAR);

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

        if (user == null) throw new UsernameNotFoundException("UNF;User not found");

        tokenRepository.deleteTokensByUser(user);
        userRepository.deleteUserByEmail(email);
    }

    public void editUser(String email, EditDTO infos) throws EmptyDTOException, UsernameNotFoundException, EmailAlreadyUsedException, InvalidPasswordException {
        if (infos.isEmpty())
            throw new EmptyDTOException("EDT;DTO is empty");

        User user = userRepository.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("UNF;User not found");

        if (infos.getAcceptsEmails() != null)
            user.setAcceptsEmails(infos.getAcceptsEmails());

        if (infos.getEmail() != null) {
            if (userRepository.findByEmail(infos.getEmail()) == null) {
                user.setEmail(email);
            } else throw new EmailAlreadyUsedException("EAU;Email already used");
        }

        if (infos.getUsername() != null) {
            user.setUsername(infos.getUsername());
        }

        if (infos.getPassword() != null) {
            user.setPassword(
                    PasswordUtilities.hashPassword(
                            PasswordUtilities.validatePassword(infos.getPassword())
                    )
            );
        }

        userRepository.save(user);
    }
}
