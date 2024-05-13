package sae.scanmedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.RevokeDTO;
import sae.scanmedback.entities.User;
import sae.scanmedback.repositories.UserRepository;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final IAuthService authService;

    public UserController(final IUserService userService, final IAuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(path = "revoke",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatusCode> revoke(@RequestBody RevokeDTO infos) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByEmail((String) userAuth.getPrincipal());
        authService.deleteToken(user, infos.getDevice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "delete",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByEmail((String)userAuth.getPrincipal());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
