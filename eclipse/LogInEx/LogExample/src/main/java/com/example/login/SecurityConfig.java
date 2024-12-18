package com.example.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/donors/login", 
                    "/api/donors/register", 
                    "/api/donors/profile/**", // Allow access to profile endpoints
                    "/api/donors/update", // Allow access to update endpoint
                    "/api/bloodbanks/**", // Allow access to blood bank endpoints
                    "/api/bloodstock/**"  // Allow access to blood stock endpoints
                ).permitAll() // Allow these endpoints without authentication
                .anyRequest().authenticated() // Require authentication for all other endpoints
            )
            .httpBasic(); // Use basic authentication (if applicable)
        return http.build();
    }
}
