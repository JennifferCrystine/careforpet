package com.gpms.petcare.service;

import com.gpms.petcare.exception.UsuarioJaAvaliouException;
import com.gpms.petcare.model.Avaliacao;
import com.gpms.petcare.model.Profissional;
import com.gpms.petcare.model.Usuario;
import com.gpms.petcare.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public Double buscaNotaPorId(Long id) {
        return avaliacaoRepository.buscaNota(id);
    }

    public Avaliacao avalia(Usuario usuario, Profissional profissional, Double nota) throws UsuarioJaAvaliouException {

        if (avaliacaoRepository.findAvaliacaoByAvaliadoAndAndAvaliador(profissional, usuario).isPresent())
            throw new UsuarioJaAvaliouException("Usuário já avaliou este profissional");
        
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAvaliado(profissional);
        avaliacao.setNota(nota);
        avaliacao.setAvaliador(usuario);

        return avaliacaoRepository.save(avaliacao);
    }
}
