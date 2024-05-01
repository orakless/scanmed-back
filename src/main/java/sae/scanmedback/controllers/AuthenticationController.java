package sae.scanmedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.LoginDTO;
import sae.scanmedback.api.dto.RegisterDTO;
import sae.scanmedback.entities.User;
import sae.scanmedback.errors.DeviceAlreadyAuthenticatedException;
import sae.scanmedback.api.response.ErrorResponse;
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
    public ResponseEntity<Object> register(@RequestBody RegisterDTO infos) {
        try {
            User newUser = userService.registerNewUser(infos);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody LoginDTO infos) {
        try {
            User loggedUser = userService.login(infos.getEmail(), infos.getPassword());

            if (loggedUser == null)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            return new ResponseEntity<>(authService.generateToken(loggedUser, infos.getDevice()).getToken(),
                    HttpStatus.CREATED);
        } catch (DeviceAlreadyAuthenticatedException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
