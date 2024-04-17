package sae.scanmedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sae.scanmedback.repositories.UtilisateurRepository;
import sae.scanmedback.security.PasswordUtilities;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UtilisateurRepository utilisateurRepository;


}
