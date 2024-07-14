package org.hsha.hsha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/references")
    public String getReferencePage() {
        return "references/reference-landing";
    }
    @GetMapping("/")
    public String getHomePage() {
        return "homepage/homepage";
    }
}
