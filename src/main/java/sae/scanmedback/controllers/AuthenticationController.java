package sae.scanmedback.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.LoginDTO;
import sae.scanmedback.api.dto.RegisterDTO;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.data.LoginData;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.DeviceAlreadyAuthenticatedException;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.errors.InvalidPasswordException;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final IUserService userService;
    private final IAuthService authService;

    public AuthenticationController(final IUserService userService, final IAuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> register(@RequestBody RegisterDTO infos) {
        try {
            User newUser = userService.registerNewUser(infos);
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

            return new ResponseEntity<>(
                    new ValidResponse("success",
                    new LoginData(authService.generateToken(loggedUser, infos.getDevice()).getToken()),
                    "Token generated"),
                    HttpStatus.CREATED);
        } catch (DeviceAlreadyAuthenticatedException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
