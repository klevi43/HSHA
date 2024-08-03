package org.hsha.hsha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/references")
    public String getReferencePage() {
        return "references/reference-landing";
    }
    @GetMapping("/")
    public String getHomePage() {
        return "homepage/index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/";
    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login/login";
//    }
}
