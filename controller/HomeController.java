package com.library.management.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String homePage(Authentication authentication, Model model) {
        model.addAttribute("auth", authentication);
        return "home"; // Looks for home.html in templates/
    }
}