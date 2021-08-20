package com.gpms.petcare.service;

import com.gpms.petcare.model.Profissional;
import com.gpms.petcare.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public Profissional cadastraProfissional(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public Profissional buscaProfissional(Long id) {
        return profissionalRepository.findById(id).orElse(null);
    }

    public Profissional alteraProfissional(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }
}
