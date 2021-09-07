package com.gpms.petcare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoServicos {

    TRATAMENTO("Tratamento"), LIMPEZA("Limpeza"), PASSEIO("Passeio");

    private String nomeHumano;

}
