package sae.scanmedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sae.scanmedback.entities.Jeton;
import sae.scanmedback.entities.Utilisateur;
import sae.scanmedback.repositories.JetonRepository;
import sae.scanmedback.repositories.UtilisateurRepository;
import sae.scanmedback.services.IUserService;
import sae.scanmedback.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    JetonRepository jetonRepository;
    @Autowired
    IUserService userService;

    @PostMapping(path = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> register(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newUtilisateur = userService.registerNewUtilisateur(utilisateur);
            return new ResponseEntity<>(newUtilisateur, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
