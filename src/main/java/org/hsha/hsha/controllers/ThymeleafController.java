package org.hsha.hsha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value="/thymeleaf")
public class ThymeleafController {
    @GetMapping("/ex01")
    public String thymeleaf01(Model model) {
        model.addAttribute("data", "타임리프 예제 입니다");
        return "thymeleafEx/thymeleafEx01";
    }
}
