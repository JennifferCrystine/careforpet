package com.gpms.petcare.controller;

import com.gpms.petcare.model.Pet;
import com.gpms.petcare.service.AdocaoService;
import com.gpms.petcare.session.UsuarioLogadoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/adocao")
public class AdocaoController {

    @Autowired
    private UsuarioLogadoSession usuarioLogadoSession;

    @Autowired
    private AdocaoService adocaoService;


    @GetMapping("/listaPet")
    public String listaPet(Model model, @RequestParam(required = false) String sucesso){

        List<Pet> pets = adocaoService.listaPet();

        model.addAttribute("pets", pets);
        model.addAttribute("colocarMensagemSucesso", !Objects.isNull(sucesso));


        return "adocao/lista-pet";
    }
    @GetMapping("/listaPetAdotado")
    public String listaAdotado(Model model){

        model.addAttribute("petsAdotados", adocaoService.listaAdotado());
        return "adocao/lista-pet";
    }

    @GetMapping("/cadastraPet")
    public String cadastraPet(Model model) {
        model.addAttribute("pet", new Pet());

        return "adocao/cadastrar";
    }

    @PostMapping("/cadastraPet")
    public String cadastraNovoPet(Pet pet, Model model) {

        try {
            adocaoService.cadastraNovoPet(pet.getNome(),pet.getRaca());
        } catch (Exception e) {
            model.addAttribute(pet);
            return "adocao/lista-pet";
        }

        return "redirect:/adocao/listaPet?sucesso";
    }

    @GetMapping("/adotaPet/{id}")
    public String adotaPet(Model model, @PathVariable Long id, @RequestParam(required = false) String erro) {

        model.addAttribute("mostrarMensagemErro", !Objects.isNull(erro));

        Pet pet = adocaoService.buscaPetPorId(id);
        model.addAttribute(pet);

        return "adocao/mostra-pet";
    }

    @PostMapping("/adotaPet")
    public String adotaPetPorId(@RequestParam Long id, Model model) throws Exception {

        try {
            adocaoService.adotaPet(id, usuarioLogadoSession.getEmail());
        } catch (Exception e) {
            return "adocao/mostra-pet/" + id + "?erro";
        }

        return "redirect:/adocao/listaPet?sucesso";
    }
}
