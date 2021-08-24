package com.gpms.petcare.repository;

import com.gpms.petcare.model.Avaliacao;
import com.gpms.petcare.model.Profissional;
import com.gpms.petcare.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    @Query("select avg(a.nota) from avaliacao a where a.avaliado.id = :id")
    Double buscaNota(@Param("id") Long profissionalId);


    Optional<Avaliacao> findAvaliacaoByAvaliadoAndAndAvaliador(Profissional profissional, Usuario usuario);
}
