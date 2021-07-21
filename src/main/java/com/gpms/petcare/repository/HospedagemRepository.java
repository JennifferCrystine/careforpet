package com.gpms.petcare.repository;

import com.gpms.petcare.model.Hospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospedagemRepository extends JpaRepository<Hospedagem, Long> {

    Optional<Hospedagem> findHospedagemById(Long id);
}
