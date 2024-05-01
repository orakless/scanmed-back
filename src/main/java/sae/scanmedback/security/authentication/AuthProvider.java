package sae.scanmedback.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sae.scanmedback.entities.User;
import sae.scanmedback.services.AuthService;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IUserService;
import sae.scanmedback.services.UserService;

@Component
public class AuthProvider implements AuthenticationProvider {
    private final IAuthService authService;

    private final IUserService userService;

    AuthProvider(final IUserService userService, final IAuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Note: Username == email, Spring Boot is just stupid
        User user = userService.loadUserByEmail(
            (String) authentication.getPrincipal()
        );

        if (user == null)
            throw new UsernameNotFoundException("Email not found");

        authentication.setAuthenticated(
                authService.checkToken(
                    user,
                    (String) authentication.getCredentials()
                ));

        if (!authentication.isAuthenticated())
            throw new BadCredentialsException("Token not valid");

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(EmailTokenAuthentication.class);
    }
}
