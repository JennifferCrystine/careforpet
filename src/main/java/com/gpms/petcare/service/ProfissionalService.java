package com.gpms.petcare.service;

import com.gpms.petcare.model.Profissional;
import com.gpms.petcare.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    public Profissional cadastraProfissional(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public Profissional buscaProfissional(Long id) {
        Optional<Profissional> profissionalOp = profissionalRepository.findById(id);
        if (profissionalOp.isEmpty())
            return null;

        Profissional profissional = profissionalOp.get();
        profissional.setNota(avaliacaoService.buscaNotaPorId(id));

        return profissional;
    }

    public Profissional alteraProfissional(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public List<Profissional> listaProfissionais() {
        return profissionalRepository.findAll();
    }
}
