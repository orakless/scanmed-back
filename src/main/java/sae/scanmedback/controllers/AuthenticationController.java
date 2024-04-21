package sae.scanmedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.api.dto.LoginDTO;
import sae.scanmedback.entities.Utilisateur;
import sae.scanmedback.errors.AppareilAlreadyAuthenticatedException;
import sae.scanmedback.api.response.ErrorResponse;
import sae.scanmedback.repositories.JetonRepository;
import sae.scanmedback.repositories.UtilisateurRepository;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    JetonRepository jetonRepository;
    @Autowired
    IUserService userService;
    @Autowired
    IAuthService authService;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> register(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newUtilisateur = userService.registerNewUtilisateur(utilisateur);
            return new ResponseEntity<>(newUtilisateur, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestBody LoginDTO infos) {
        try {
            Utilisateur loggedUtilisateur = userService.login(infos.getMail(), infos.getMotDePasse());

            if (loggedUtilisateur == null)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            return new ResponseEntity<>(authService.generateJeton(loggedUtilisateur, infos.getNomAppareil()).getJeton(), HttpStatus.CREATED);
        } catch (AppareilAlreadyAuthenticatedException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
