package com.gpms.petcare.controller;

import com.gpms.petcare.dto.ReservaResponseDTO;
import com.gpms.petcare.enums.TipoHospedagem;
import com.gpms.petcare.model.Hospedagem;
import com.gpms.petcare.service.HospedagemService;
import com.gpms.petcare.session.UsuarioLogadoSession;
import com.gpms.petcare.validator.HospedagemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public String listarHospedagens(Model model, @RequestParam(required = false) String sucesso, @RequestParam(required = false) String filtrar,
                                    @RequestParam(required = false) String dataInicio,
                                    @RequestParam(required = false) String dataFinal,
                                    @RequestParam(required = false) Double valorMinimo,
                                    @RequestParam(required = false) Double valorMaximo,
                                    @RequestParam(required = false) String tipo) {
        List<Hospedagem> hospedagens = hospedagemService.getAll();
        if (!Objects.isNull(filtrar)) {
            String finalTipo = tipo.toUpperCase();
            hospedagens = hospedagens.stream().filter(h->{
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date inicio = sdf.parse(dataInicio);
                    Date finale = sdf.parse(dataFinal);
                    long diffInMillies = Math.abs(finale.getTime() - inicio.getTime());
                    long dias = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    Double valor = dias*h.getValorDiaria();
                    Boolean result = true;

                    if (!Objects.isNull(valorMinimo) && !Objects.isNull(valorMaximo))
                        result = valor >= valorMinimo && valor <= valorMaximo;

                    if (!Objects.isNull(dataFinal) && !Objects.isNull(dataInicio)) {
                        h.setValorTotal(valor);
                    } else {
                        h.setValorTotal(h.getValorDiaria());
                    }

                    if (!"NULL".equals(finalTipo))
                        result = result && !Objects.isNull(h.getTipoHospedagem()) &&  h.getTipoHospedagem().equals(finalTipo);

                    return result;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }).collect(Collectors.toList());
        }

        model.addAttribute("colocarMensagemSucesso", !Objects.isNull(sucesso));
        model.addAttribute("hospedagens", hospedagens);

        return "hospedagem/listar";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {

        Long usuarioId = usuarioLogadoSession.getId();

        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setUsuarioId(usuarioId);
        model.addAttribute("hospedagem", hospedagem);
        model.addAttribute("pagina", "cadastrar");
        model.addAttribute("tipos", TipoHospedagem.values());


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
            model.addAttribute("pagina", "cadastrar");
            model.addAttribute("errorMessage", "erro ao cadastrar hospedagem");
            model.addAttribute("hospedagem", hospedagem);
            return "hospedagem/cadastrar-editar";
        }

        hospedagemService.criarHospedagem(hospedagem);

        return "redirect:/hospedagem/listar?sucesso";
    }

    @GetMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id, Model model) {

        Hospedagem hospedagem = hospedagemService.getById(id).get();

        model.addAttribute("hospedagem", hospedagem);
        model.addAttribute("pagina", "atualizar");

        return "hospedagem/cadastrar-editar";
    }

    @GetMapping("/reservar/{id}")
    public String reservar(@PathVariable Long id, Model model) {

        Hospedagem hospedagem = hospedagemService.getById(id).get();

        model.addAttribute("hospedagem", hospedagem);


        return "hospedagem/reservar";
    }

    @GetMapping("/reservar")
    @ResponseBody
    public ReservaResponseDTO reservar(@RequestParam Long id,
                                       @RequestParam String dataInicio,
                                       @RequestParam String dataFinal) throws ParseException {
        Hospedagem hospedagem = hospedagemService.getById(id).get();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date inicio = sdf.parse(dataInicio);
        Date finale = sdf.parse(dataFinal);
        long diffInMillies = Math.abs(finale.getTime() - inicio.getTime());
        long dias = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double valorTotal = dias*hospedagem.getValorDiaria();
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setValor(new DecimalFormat("#,##0.00").format(valorTotal));
        dto.setChavePix(hospedagem.getChavePix());

        return dto;

    }

    @PostMapping("/atualizar")
    public String atualizar(Hospedagem hospedagem, Model model, Errors errors, HttpServletRequest request) {

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
            model.addAttribute("pagina", "atualizar");
            model.addAttribute("errorMessage", "erro ao atualizar hospedagem");
            model.addAttribute("hospedagem", hospedagem);
            return "hospedagem/cadastrar-editar";
        }

        hospedagemService.atualizarHospedagem(hospedagem);

        return "redirect:/hospedagem/listar?sucesso";
    }
}
