package sae.scanmedback.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sae.scanmedback.api.dto.EditDTO;
import sae.scanmedback.api.dto.RegisterDTO;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.EmailAlreadyUsedException;
import sae.scanmedback.errors.EmptyDTOException;
import sae.scanmedback.errors.InvalidPasswordException;

public interface IUserService {
    User registerNewUser(RegisterDTO infos) throws InvalidPasswordException;
    User loadUserByEmail(String email);
    User login(String email, String password);
    void deleteUserByEmail(String email);
    void editUser(String email, EditDTO infos) throws EmptyDTOException, UsernameNotFoundException, EmailAlreadyUsedException, InvalidPasswordException;
}
