package sae.scanmedback.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sae.scanmedback.api.response.IResponse;
import sae.scanmedback.api.response.ValidResponse;
import sae.scanmedback.api.response.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.EditDTO;
import sae.scanmedback.api.dto.RevokeDTO;
import sae.scanmedback.entities.User;
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

    @GetMapping(path = "delete",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> delete() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByEmail((String)userAuth.getPrincipal());
        return new ResponseEntity<>(
                new ValidResponse("success",
                        null,
                        "User deleted"),
                HttpStatus.OK);
    }

    @PostMapping(path = "edit",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IResponse> edit(@RequestBody EditDTO infos) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();
        try {
           userService.editUser((String) userAuth.getPrincipal(), infos);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new ValidResponse("success", null, "User modified"),
                HttpStatus.OK);
    }
}
