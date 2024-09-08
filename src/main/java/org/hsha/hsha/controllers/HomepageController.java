package org.hsha.hsha.controllers;

import org.hsha.hsha.models.User;
import org.hsha.hsha.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.rmi.ServerException;
import java.util.Optional;

@Controller
public class HomepageController {
    @Autowired
    private UserService userService;



        @GetMapping("/")
    public String getHomePage(Model model) throws ServerException {
            if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                String email = SecurityContextHolder.getContext().getAuthentication().getName();
                Optional<User> user = userService.retrieveUserByEmail(email);
                if (user.isPresent()) {
                    model.addAttribute("user", user);
                }
            }
            return "homepage/index";
    }






//    @PostMapping("/login")
//    public String login() {
//        return "redirect:/";
//    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login/login";
//    }
}
