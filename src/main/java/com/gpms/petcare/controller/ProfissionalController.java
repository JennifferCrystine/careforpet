package com.gpms.petcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpms.petcare.client.LocalidadesAPIClient;
import com.gpms.petcare.dto.localidadesAPIClient.EstadoDTO;
import com.gpms.petcare.enums.TipoHospedagem;
import com.gpms.petcare.model.Profissional;
import com.gpms.petcare.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private LocalidadesAPIClient localidadesAPIClient;

    @GetMapping("/cadastrar")
    public String cadastrar(Model model,
                            @RequestParam(name = "uf") Optional<String> estadoOp,
                            @RequestParam(name = "nome") Optional<String> nomeOp,
                            @RequestParam(name = "email") Optional<String> emailOp,
                            @RequestParam(name = "chavePix") Optional<String> chavePixOp,
                            @RequestParam(name = "telefone") Optional<String> telefoneOp) throws JsonProcessingException {


        Profissional profissional = new Profissional();
        nomeOp.ifPresent(profissional::setNome);
        emailOp.ifPresent(profissional::setEmail);
        chavePixOp.ifPresent(profissional::setChavePix);
        telefoneOp.ifPresent(profissional::setTelefone);

        model.addAttribute("profissional", profissional);
        model.addAttribute("pagina", "cadastrar");

        EstadoDTO[] estadoDTOs = localidadesAPIClient.getTodosEstados();
        List<String> estadosSiglas = Arrays.stream(estadoDTOs)
                .map(EstadoDTO::getSigla).collect(Collectors.toList());

        List<String> cidades = new ArrayList<>();

        String estadoSelecionado = "";

        if (estadoOp.isPresent() && estadosSiglas.contains(estadoOp.get())) {
            EstadoDTO estadoDTO = Arrays.stream(estadoDTOs)
                    .filter(dto -> dto.getSigla().equals(estadoOp.get()))
                    .findFirst().get();

            estadoSelecionado = estadoDTO.getSigla();

            cidades = localidadesAPIClient.getCidadesPorEstado(estadoDTO.getId()).stream().map(cidadeObj -> {
                LinkedHashMap cidadeMap = (LinkedHashMap) cidadeObj;
                return (String) cidadeMap.get("nome");
            }).collect(Collectors.toList());

            cidades.sort(Comparator.naturalOrder());
        }
        estadosSiglas.sort(Comparator.naturalOrder());
        model.addAttribute("estados", estadosSiglas);
        model.addAttribute("cidades", cidades);
        model.addAttribute("estadoSelecionado", estadoSelecionado);

        return "profissional/cadastrar-editar";
    }
}
