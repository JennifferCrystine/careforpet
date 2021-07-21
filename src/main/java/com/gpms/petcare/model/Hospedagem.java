package com.gpms.petcare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="hospedagem")
public class Hospedagem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name="chave_pix")
    private String chavePix;

    @Column(name="valor_diaria")
    private Double valorDiaria;

    @Column(name="usuario_id")
    private Long usuarioId;

}