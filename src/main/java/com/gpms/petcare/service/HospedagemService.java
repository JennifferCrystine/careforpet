package com.gpms.petcare.service;

import com.gpms.petcare.model.Hospedagem;
import com.gpms.petcare.repository.HospedagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedagemService {

    @Autowired
    private HospedagemRepository hospedagemRepository;

    public List<Hospedagem> getAll() {
        return hospedagemRepository.findAll();
    }

    public Optional<Hospedagem> getById(Long id) {
        return hospedagemRepository.findHospedagemById(id);
    }

    public Hospedagem criarHospedagem(Hospedagem h) {
        return hospedagemRepository.save(h);
    }

    public Hospedagem atualizarHospedagem(Hospedagem h) {
        return hospedagemRepository.save(h);
    }

}
