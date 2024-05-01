package sae.scanmedback.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sae.scanmedback.repositories.UserRepository;
import sae.scanmedback.security.authentication.AuthenticationFilter;
import sae.scanmedback.services.AuthService;
import sae.scanmedback.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Autowired
    AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(
                (session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                (authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
            )
            .exceptionHandling(
                (exHandling) -> exHandling
                        .authenticationEntryPoint(
                                (request, response, ex) -> {response
                                        .sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED,
                                                "boohoo"
                                        );
                                })
            )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
