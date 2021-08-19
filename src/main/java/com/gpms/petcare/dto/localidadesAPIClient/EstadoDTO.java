package com.gpms.petcare.dto.localidadesAPIClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDTO {
    private Long id;

    private String nome;

    private String sigla;

    private RegiaoDTO regiao;

}
