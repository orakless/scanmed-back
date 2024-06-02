package sae.scanmedback.controllers;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.EditDTO;
import sae.scanmedback.api.dto.RevokeDTO;
import sae.scanmedback.api.response.data.UserData;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.EmailAlreadyUsedException;
import sae.scanmedback.errors.EmptyDTOException;
import sae.scanmedback.errors.InvalidPasswordException;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IMailService;
import sae.scanmedback.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final IAuthService authService;
    private final IMailService mailService;

    public UserController(final IUserService userService, final IAuthService authService,
                          final IMailService mailService) {
        this.userService = userService;
        this.authService = authService;
        this.mailService = mailService;
    }

    @DeleteMapping(path = "revoke",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> revoke(@RequestBody RevokeDTO infos) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
        authService.deleteToken(user, infos.getDevice());
        return new ResponseEntity<>(
                new ValidResponse(
                        "success",
                        null,
                        "Token revoked"
                ),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "delete",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> delete() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) userAuth.getPrincipal();
        userService.deleteUserByEmail(email);
        try {
            mailService.sendAccountDeletionMail(email);
        } catch (MailException | MessagingException ex) {
            System.err.println(ex.getMessage());
        }
        return new ResponseEntity<>(
                new ValidResponse("success",
                        null,
                        "User deleted"),
                HttpStatus.OK);
    }

    @PatchMapping(path = "edit",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> edit(@RequestBody EditDTO infos) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
           userService.editUser((String) userAuth.getPrincipal(), infos);
        } catch (EmptyDTOException | UsernameNotFoundException | EmailAlreadyUsedException | InvalidPasswordException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;Could not edit user informations."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                new ValidResponse("success", null, "User modified"),
                HttpStatus.OK);
    }

    @GetMapping(path = "infos",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> infos() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
            return new ResponseEntity<>(
                    new ValidResponse("success",
                            new UserData(user.getDisplayName(), user.getUsername(), user.acceptsEmails(), user.getAvatar()),
                            null), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;Could not edit user informations."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
