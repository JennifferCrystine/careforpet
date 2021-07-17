package com.gpms.petcare.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExemploController {

    @GetMapping("/exemplo")
    public String alomundo(Model model) {
        model.addAttribute("nome", "Jenniffer");
        return "exemplo";
    }
}
