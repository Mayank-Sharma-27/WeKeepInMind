package com.wekeepinmind.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

@Configuration // Marks this class as a source of bean definitions for Spring's IoC container.
@EnableWebSecurity // Enables Spring Security's web security support and provides the Spring MVC integration.
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean // Defines the security filter chain bean responsible for configuring HTTP security.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable).
                authorizeRequests()
                .anyRequest()
                .permitAll();

        return http.build();
    }
}
