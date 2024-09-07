package com.wekeepinmind.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a source of bean definitions for Spring's IoC container.
@EnableWebSecurity // Enables Spring Security's web security support and provides the Spring MVC integration.
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthAuthenticationHandler oAuthAuthenticationHandler;

    @Bean // Defines the security filter chain bean responsible for configuring HTTP security.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuring HttpSecurity: it customizes how requests are authorized.
        http
                .authorizeHttpRequests(authorizeRequests // Begin configuring authorization for incoming HTTP requests.
                        -> authorizeRequests
                        // Allows unrestricted access to the "/login" endpoint (no authentication required).
                        .requestMatchers("/google-login").permitAll()
                        // All other requests require authentication to access.
                        .anyRequest().authenticated()
                );

        // Builds the security configuration.
        return http.build();
    }
}
