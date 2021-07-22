package com.gpms.petcare.controller;

import com.gpms.petcare.model.Adocao;
import com.gpms.petcare.model.Pet;
import com.gpms.petcare.repository.AdocaoRepository;
import com.gpms.petcare.service.AdocaoService;
import com.gpms.petcare.session.UsuarioLogadoSession;
import com.sun.istack.NotNull;
import java.util.List;
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


    @GetMapping("/listaPet")
    public ResponseEntity<List<Pet>> listaPet(){
        return ResponseEntity.ok().body(adocaoService.listaPet());
    }
    @GetMapping("/listaPetAdotado")
    public ResponseEntity<List<Adocao>> listaAdotado(){
        return ResponseEntity.ok().body(adocaoService.listaAdotado());
    }
    @PostMapping("/cadastraPet")
    public ResponseEntity<Pet> cadastraNovoPet(@RequestParam String nome, @RequestParam String raça) throws Exception {

        return ResponseEntity.ok().body(adocaoService.cadastraNovoPet(nome,raça));
    }

    @PostMapping("/adotaPet")
    public ResponseEntity<Adocao> adotaPetPorId(@RequestParam Long id) throws Exception {

        return ResponseEntity.ok().body(adocaoService.adotaPet(id, usuarioLogadoSession.getEmail()));
    }
}
