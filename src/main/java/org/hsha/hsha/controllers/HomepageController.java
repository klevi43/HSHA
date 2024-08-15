package org.hsha.hsha.controllers;

import org.hsha.hsha.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
    @Autowired
    private UserService userService;



        @GetMapping("/")
    public String getHomePage() {

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
