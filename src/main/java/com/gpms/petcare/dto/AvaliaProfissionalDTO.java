package com.gpms.petcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliaProfissionalDTO {

    private Long profissionalId;

    private Long usuarioId;

    private Double nota;

    private String nome;

    private String servico;
}
