package com.gpms.petcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExemploController {

    @GetMapping("/ongs")
    public String alomundo(Model model) {
        model.addAttribute("nome", "Jenniffer");
        return "ongs";
    }

    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/adote")
    public String adocao() {
        return "adocao";
    }
}
