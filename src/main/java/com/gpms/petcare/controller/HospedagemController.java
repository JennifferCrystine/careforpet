package com.gpms.petcare.controller;

import com.gpms.petcare.model.Hospedagem;
import com.gpms.petcare.service.HospedagemService;
import com.gpms.petcare.session.UsuarioLogadoSession;
import com.gpms.petcare.validator.HospedagemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("/hospedagem")
public class HospedagemController {

    @Autowired
    private UsuarioLogadoSession usuarioLogadoSession;

    @Autowired
    private HospedagemValidator validator;

    @Autowired
    private HospedagemService hospedagemService;

    @GetMapping("/listar")
    public String listarHospedagens(Model model) {
        List<Hospedagem> hospedagens = hospedagemService.getAll();

        model.addAttribute("hospedagens", hospedagens);

        return "hospedagem/listar";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {

        Long usuarioId = usuarioLogadoSession.getId();

        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setUsuarioId(usuarioId);
        model.addAttribute("hospedagem", hospedagem);


        return "hospedagem/cadastrar-editar";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Hospedagem hospedagem, Errors errors, Model model, HttpServletRequest request) {
        int menosErro = 0;
        if (Objects.isNull(hospedagem.getValorDiaria())) {
            String paramValorDiaria = request.getParameter("valorDiaria");
            String regex = "\\d+,\\d+";
            if (!Objects.isNull(paramValorDiaria) && paramValorDiaria.matches(regex)) {
                hospedagem.setValorDiaria(Double.parseDouble(paramValorDiaria.replaceAll(",", ".")));
                menosErro = 1;
            }
        }

        validator.validate(hospedagem, errors);

        if (errors.getErrorCount() - menosErro > 0) {
            model.addAttribute("pagina", "Cadastrar");
            model.addAttribute("errorMessage", "erro ao cadastrar hospedagem");
            model.addAttribute("hospedagem", hospedagem);
            return "hospedagem/cadastrar-editar";
        }

        hospedagemService.criarHospedagem(hospedagem);
        return null;
    }

    @GetMapping("/atualizar")
    public String atualizar(Long id, Model model) {

        Hospedagem hospedagem = hospedagemService.getById(id).get();

        model.addAttribute("hospedagem", hospedagem);

        return "hospedagem/cadastrar-editar";
    }

    @PostMapping("/atualizar")
    public String atualizar(Hospedagem hospedagem, Model model, Errors errors) {

        validator.validate(hospedagem, errors);

        if (errors.hasErrors()) {
            model.addAttribute("pagina", "Atualizar");
            model.addAttribute("errorMessage", "erro ao cadastrar hospedagem");
            model.addAttribute("hospedagem", hospedagem);
            return "hospedagem/cadastrar-editar";
        }

        hospedagemService.atualizarHospedagem(hospedagem);
        return null;
    }
}
