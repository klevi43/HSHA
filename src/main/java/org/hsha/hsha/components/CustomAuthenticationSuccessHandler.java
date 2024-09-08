package org.hsha.hsha.components;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hsha.hsha.models.User;
import org.hsha.hsha.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {





        String redirectURL = request.getContextPath();
        if (authentication.getAuthorities().stream().anyMatch(admin -> admin.getAuthority().equals("ROLE_ADMIN"))) {
            redirectURL += "/admin/home";
        }
        else if (authentication.getAuthorities().stream().anyMatch(user -> user.getAuthority().equals("ROLE_USER"))) {
            Optional<User> user = userService.retrieveUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            redirectURL += "/users/" + user.get().getId().toString() + "/workouts";
        }
        response.sendRedirect(redirectURL);
    }


}
