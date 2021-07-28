package com.gpms.petcare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum TipoHospedagem {

    HOTEL("Hotel"), PARCEIRO("Parceiro"), SPA("Spa");

    private String nome;

}
