package sae.scanmedback.controllers;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.*;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.data.LoginData;
import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.DeviceAlreadyAuthenticatedException;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.errors.EmailAlreadyUsedException;
import sae.scanmedback.errors.EmptyDTOException;
import sae.scanmedback.errors.InvalidPasswordException;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IMailService;
import sae.scanmedback.services.IPasswordResetService;
import sae.scanmedback.services.IUserService;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final IUserService userService;
    private final IAuthService authService;
    private final IMailService mailService;
    private final IPasswordResetService passwordResetService;
    public AuthenticationController(final IUserService userService, final IAuthService authService,
                                    final IMailService mailService, final IPasswordResetService passwordResetService) {
        this.userService = userService;
        this.authService = authService;
        this.mailService = mailService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> register(@RequestBody RegisterDTO infos) {
        try {
            User newUser = userService.registerNewUser(infos);
            try {
               mailService.sendAccountCreationMail(newUser);
            } catch (MailException | MessagingException ex) {
                System.err.println(ex.getMessage());
            }
            return new ResponseEntity<>(
                    new ValidResponse("success", null, "User created"),
                    HttpStatus.CREATED);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> login(@RequestBody LoginDTO infos) {
        try {
            User loggedUser = userService.login(infos.getEmail(), infos.getPassword());

            if (loggedUser == null)
                return new ResponseEntity<>(new ErrorResponse("UME;User not found"), HttpStatus.UNAUTHORIZED);

            Token newToken = authService.generateToken(loggedUser, infos.getDevice());

            try {
                mailService.sendTokenGenerationMail(loggedUser, newToken);
            } catch (MailException | MessagingException ex) {
                System.err.println(ex.getMessage());
            }

            return new ResponseEntity<>(
                    new ValidResponse("success",
                    new LoginData(newToken.getToken()),
                    "Token generated"),
                    HttpStatus.CREATED);
        } catch (DeviceAlreadyAuthenticatedException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(path = "/password_reset/request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> requestPasswordReset(@RequestBody PasswordResetRequestDTO infos) {
        try {
            userService.loadUserByEmail(infos.getEmail());
            String token = passwordResetService.generateToken(infos.getEmail());

            try {
                mailService.sendPasswordResetMail(infos.getEmail(), token);
            } catch (MailException | MessagingException ex) {
                System.err.println(ex.getMessage());
            }
        } catch (UsernameNotFoundException e) {
            System.err.println("User" + infos.getEmail() + "does not exists.");
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ValidResponse(
                    "success",
                     null,
                    "If an account with this email exists, a mail has been sent."
            ), HttpStatus.OK);

    }

    @PostMapping(path = "/password_reset",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> resetPassword(@RequestBody ResetPasswordDTO infos) {
        try {
            User user = userService.loadUserByEmail(infos.getEmail());
            if (passwordResetService.verifyToken(infos.getEmail(), infos.getToken())) {
                EditDTO passwordReset = new EditDTO();
                passwordReset.setPassword(infos.getNewPassword());
                userService.editUser(infos.getEmail(), passwordReset);
            }

            try {
                mailService.sendPasswordResetConfirmationMail(infos.getEmail());
            } catch (MailException | MessagingException ex) {
                System.err.println(ex.getMessage());
            }

            return new ResponseEntity<>(new ValidResponse(
                    "success",
                    null,
                    "Password changed successfully."
            ), HttpStatus.OK);
        } catch (InvalidPasswordException | EmptyDTOException | EmailAlreadyUsedException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("UNK;Internal server error"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
