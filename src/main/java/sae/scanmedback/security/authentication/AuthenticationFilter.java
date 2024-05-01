package sae.scanmedback.security.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sae.scanmedback.services.IAuthService;
import sae.scanmedback.services.IUserService;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    IUserService userService;
    @Autowired
    IAuthService authService;

    /*
    public AuthenticationFilter(IUserService userService, IAuthService authService) {
        this.authService = authService;
        this.userService = userService;
    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String email = request.getHeader("X-Email");
        final String token = request.getHeader("X-Token");

        if (email != null && token != null) {
            AuthProvider authProvider = new AuthProvider(userService, authService);
            Authentication userInformations = authProvider.authenticate(
                    new EmailTokenAuthentication(email, token)
            );
            SecurityContextHolder.getContext().setAuthentication(userInformations);
        }

        filterChain.doFilter(request, response);
    }
}
