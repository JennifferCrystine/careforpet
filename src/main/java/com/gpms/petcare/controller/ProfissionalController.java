package com.gpms.petcare.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gpms.petcare.client.LocalidadesAPIClient;
import com.gpms.petcare.dto.AgendarPasseioDTO;
import com.gpms.petcare.dto.AvaliaProfissionalDTO;
import com.gpms.petcare.dto.localidadesAPIClient.EstadoDTO;
import com.gpms.petcare.enums.TipoHospedagem;
import com.gpms.petcare.enums.TipoServicos;
import com.gpms.petcare.model.Avaliacao;
import com.gpms.petcare.model.Passeio;
import com.gpms.petcare.model.Profissional;
import com.gpms.petcare.model.Usuario;
import com.gpms.petcare.service.AvaliacaoService;
import com.gpms.petcare.service.PasseioService;
import com.gpms.petcare.service.ProfissionalService;
import com.gpms.petcare.service.UsuarioService;
import com.gpms.petcare.session.UsuarioLogadoSession;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private LocalidadesAPIClient localidadesAPIClient;

    @Autowired
    private UsuarioLogadoSession usuarioLogadoSession;

    @Autowired
    private PasseioService passeioService;

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
        model.addAttribute("servicos", Arrays.stream(TipoServicos.values()).map(TipoServicos::getNomeHumano).collect(Collectors.toList()));

        EstadoDTO[] estadoDTOs = localidadesAPIClient.getTodosEstados();
        List<String> estadosSiglas = Arrays.stream(estadoDTOs)
                .map(EstadoDTO::getSigla).collect(Collectors.toList());

        List<String> cidades;

        String estadoSelecionado = "";

        EstadoDTO estadoDTO;
        if (estadoOp.isPresent() && estadosSiglas.contains(estadoOp.get())) {
            estadoDTO = Arrays.stream(estadoDTOs)
                    .filter(dto -> dto.getSigla().equals(estadoOp.get()))
                    .findFirst().get();
        } else {
            estadoDTO = Arrays.stream(estadoDTOs)
                    .filter(dto -> dto.getSigla().equals("RJ"))
                    .findFirst().get();
        }

        estadoSelecionado = estadoDTO.getSigla();

        cidades = localidadesAPIClient.getCidadesPorEstado(estadoDTO.getId()).stream().map(cidadeObj -> {
            LinkedHashMap cidadeMap = (LinkedHashMap) cidadeObj;
            return (String) cidadeMap.get("nome");
        }).collect(Collectors.toList());

        cidades.sort(Comparator.naturalOrder());

        estadosSiglas.sort(Comparator.naturalOrder());
        model.addAttribute("estados", estadosSiglas);
        model.addAttribute("cidades", cidades);
        model.addAttribute("estadoSelecionado", estadoSelecionado);

        return "profissional/cadastrar-editar";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(Model model,
                            @RequestParam(name = "uf") Optional<String> estadoOp,
                            @RequestParam(name = "nome") Optional<String> nomeOp,
                            @RequestParam(name = "email") Optional<String> emailOp,
                            @RequestParam(name = "chavePix") Optional<String> chavePixOp,
                            @RequestParam(name = "telefone") Optional<String> telefoneOp,
                            @PathVariable Long id) throws JsonProcessingException {


        Profissional profissional = profissionalService.buscaProfissional(id);
        nomeOp.ifPresent(profissional::setNome);
        emailOp.ifPresent(profissional::setEmail);
        chavePixOp.ifPresent(profissional::setChavePix);
        telefoneOp.ifPresent(profissional::setTelefone);

        model.addAttribute("profissional", profissional);
        model.addAttribute("pagina", "alterar");
        model.addAttribute("servicos", Arrays.stream(TipoServicos.values()).map(TipoServicos::getNomeHumano).collect(Collectors.toList()));

        EstadoDTO[] estadoDTOs = localidadesAPIClient.getTodosEstados();
        List<String> estadosSiglas = Arrays.stream(estadoDTOs)
                .map(EstadoDTO::getSigla).collect(Collectors.toList());

        List<String> cidades;

        String estadoSelecionado = "";

        EstadoDTO estadoDTO;

        if (estadoOp.isPresent() && estadosSiglas.contains(estadoOp.get())) {
            estadoDTO = Arrays.stream(estadoDTOs)
                    .filter(dto -> dto.getSigla().equals(estadoOp.get()))
                    .findFirst().get();

        } else {
            estadoDTO = Arrays.stream(estadoDTOs)
                    .filter(dto -> dto.getSigla().equals("RJ"))
                    .findFirst().get();
        }

        estadoSelecionado = estadoDTO.getSigla();

        cidades = localidadesAPIClient.getCidadesPorEstado(estadoDTO.getId()).stream().map(cidadeObj -> {
            LinkedHashMap cidadeMap = (LinkedHashMap) cidadeObj;
            return (String) cidadeMap.get("nome");
        }).collect(Collectors.toList());

        cidades.sort(Comparator.naturalOrder());

        estadosSiglas.sort(Comparator.naturalOrder());
        model.addAttribute("estados", estadosSiglas);
        model.addAttribute("cidades", cidades);
        model.addAttribute("estadoSelecionado", estadoSelecionado);

        return "profissional/cadastrar-editar";
    }

    @GetMapping("/listar")
    public String listaProfissionais(Model model, @RequestParam(name = "uf") Optional<String> estadoOp, @RequestParam(name="cidade") Optional<String> cidadeOp) throws JsonProcessingException {

        List<Profissional> profissionais = profissionalService.listaProfissionais();
        if (estadoOp.isPresent() && !estadoOp.get().equals("")){
            profissionais = profissionais.stream().filter(p -> p.getEstado().equals(estadoOp.get())).collect(Collectors.toList());

        }


        String cidadeSelecionada = "";
        if (cidadeOp.isPresent() && !cidadeOp.get().equals("")) {
            profissionais = profissionais.stream().filter(p -> {
                try {
                    return p.getCidade().equals(cidadeOp.get());
                } catch (NullPointerException ne) {
                    return false;
                }

            }).collect(Collectors.toList());
        }

        List<EstadoDTO> estadosDTOs = Arrays.asList(localidadesAPIClient.getTodosEstados());
        List<String> cidades = new ArrayList<>();

        String estadoSelecionado = "";

        Optional<EstadoDTO> estadoDTOselecionadoOp;


        if (estadoOp.isPresent() && !estadoOp.get().equals("")) {
            estadoDTOselecionadoOp = estadosDTOs.stream().filter(e -> e.getSigla().equals(estadoOp.get())).findFirst();
            if (estadoDTOselecionadoOp.isPresent()) {
                estadoSelecionado = estadoDTOselecionadoOp.get().getSigla();
                cidades = localidadesAPIClient.getCidadesPorEstado(estadoDTOselecionadoOp.get().getId()).stream().map(cidadeObj -> {
                    LinkedHashMap cidadeMap = (LinkedHashMap) cidadeObj;
                    return (String) cidadeMap.get("nome");
                }).collect(Collectors.toList());
            }

            if (cidadeOp.isPresent() && cidades.contains(cidadeOp.get())) {
                cidadeSelecionada = cidadeOp.get();
            }
        }

        List<String> estados = estadosDTOs.stream().map(EstadoDTO::getSigla).collect(Collectors.toList());
        estados.sort(Comparator.naturalOrder());

        model.addAttribute("estados", estadosDTOs.stream().map(EstadoDTO::getSigla).collect(Collectors.toList()));
        model.addAttribute("cidades", cidades);
        model.addAttribute("estadoSelecionado", estadoSelecionado);
        model.addAttribute("cidadeSelecionada", cidadeSelecionada);
        model.addAttribute("profissionais", profissionais);
        return "profissional/listar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Profissional profissional) {
        Profissional novoProfissional = profissionalService.cadastraProfissional(profissional);

        return "redirect:/profissional/" + novoProfissional.getId();
    }

    @PostMapping("/alterar")
    public String alterar(Profissional profissional) {
        profissionalService.alteraProfissional(profissional);

        return "redirect:/profissional/" + profissional.getId();
    }

    @GetMapping("/{id}")
    public String perfil(Model model, @PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioPorId(usuarioLogadoSession.getId()).get();
        Profissional profissional = profissionalService.buscaProfissional(id);
        Boolean usuarioPodeAvaliar = !avaliacaoService.usuarioAvaliouProfissional(usuario, profissional);

        model.addAttribute("podeAvaliar", usuarioPodeAvaliar);
        model.addAttribute(profissional);

        return "profissional/perfil";
    }

    @GetMapping("/avalia/{id}")
    public String avalia(Model model, @PathVariable(name = "id") Long profissionalId) {
        AvaliaProfissionalDTO dto = new AvaliaProfissionalDTO();
        Profissional profissional = profissionalService.buscaProfissional(profissionalId);
        dto.setProfissionalId(profissionalId);
        dto.setUsuarioId(usuarioLogadoSession.getId());
        dto.setServico(profissional.getServico());
        dto.setNome(profissional.getNome());
        model.addAttribute(dto);

        return "profissional/avalia";
    }

    @PostMapping("/avalia")
    public String avalia(AvaliaProfissionalDTO dto, Model model) {

        try {
            Profissional profissional = profissionalService.buscaProfissional(dto.getProfissionalId());
            Usuario usuario = usuarioService.getUsuarioPorId(dto.getUsuarioId()).orElse(null);

            if (Objects.isNull(profissional) || Objects.isNull(usuario))
                throw new Exception("Usuario ou Profissional nulo");

            avaliacaoService.avalia(usuario, profissional, dto.getNota());

            return "redirect:/profissional/" + profissional.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao avaliar");
            model.addAttribute(dto);
            return "profissional/avalia";
        }
    }

    @GetMapping("/agendar/{id}")
    public String agenda(@PathVariable(name = "id") Long profissionalId, Model model) throws NotFoundException {

        Profissional profissional = profissionalService.buscaProfissional(profissionalId);

        if (Objects.isNull(profissional))
            throw new NotFoundException("Profissional não encontrado");

        AgendarPasseioDTO agendarPasseioDTO = new AgendarPasseioDTO();
        agendarPasseioDTO.setUsuarioId(usuarioLogadoSession.getId());
        agendarPasseioDTO.setProfissionalId(profissionalId);

        model.addAttribute("dto", agendarPasseioDTO);
        model.addAttribute("nome", profissional.getNome());
        model.addAttribute("telefone", profissional.getTelefone());

        return "profissional/agendar";

    }

    @PostMapping("/agendar")
    public String agendar(AgendarPasseioDTO dto, Model model) {

        Passeio passeio = new Passeio();

        passeio.setProfissionalId(dto.getProfissionalId());
        passeio.setUsuarioId(usuarioLogadoSession.getId());

        passeio.setData(dto.getData());

        Passeio novoPasseio = passeioService.criaPasseio(passeio);

        Profissional profissional = profissionalService.buscaProfissional(novoPasseio.getProfissionalId());

        model.addAttribute("profissional", profissional);
        model.addAttribute("passeio", novoPasseio);
        return "profissional/agendadoSucesso";
    }

}
