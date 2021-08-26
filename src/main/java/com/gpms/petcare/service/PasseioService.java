package com.gpms.petcare.service;

import com.gpms.petcare.model.Passeio;
import com.gpms.petcare.repository.PasseioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasseioService {

    @Autowired
    private PasseioRepository passeioRepository;

    public Passeio criaPasseio(Passeio passeio) {
        return passeioRepository.save(passeio);
    }
}
