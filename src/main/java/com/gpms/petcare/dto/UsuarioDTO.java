package com.gpms.petcare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private String email;

    private String senha;

    private String confirmacaoSenha;
}
