package com.gpms.petcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendarPasseioDTO {

    private String data;

    private Long usuarioId;

    private Long profissionalId;

}
