package com.gpms.petcare.controller;

import com.gpms.petcare.model.Pet;
import com.gpms.petcare.repository.AdocaoRepository;
import com.gpms.petcare.service.AdocaoService;
import com.gpms.petcare.session.UsuarioLogadoSession;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/adocao")
public class AdocaoController {

    @Autowired
    private UsuarioLogadoSession usuarioLogadoSession;

    @Autowired
    private AdocaoService adocaoService;



    @PostMapping("/cadastra")
    public ResponseEntity<Pet> cadastraNovoPet(@RequestParam String nome, @RequestParam String raça) throws Exception {

        return ResponseEntity.ok().body(adocaoService.cadastraNovoPet(nome,raça));
    }
}
