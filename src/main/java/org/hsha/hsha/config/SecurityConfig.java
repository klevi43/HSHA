package org.hsha.hsha.config;



import org.hsha.hsha.components.CustomAuthenticationSuccessHandler;
import org.hsha.hsha.components.UserSecurity;

import org.hsha.hsha.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {


    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserSecurity userSecurity) throws Exception {


        // define which pages are visible to non-users
        http.authorizeHttpRequests(configurer -> configurer.requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/users/register").permitAll()
                        .requestMatchers("/users/{userId}/**").access(userSecurity)
                        .requestMatchers("/logout").permitAll()
                .anyRequest().authenticated()
                );

        // set up form for login
        http.formLogin(form -> form.loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")

                        .successHandler(customSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
