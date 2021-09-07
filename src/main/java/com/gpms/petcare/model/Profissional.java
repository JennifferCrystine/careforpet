package com.gpms.petcare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name="profissional")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column
    private String chavePix;

    @Transient
    private Double nota;

    @Column
    private Double preco;

    @Column
    private String estado;

    @Column
    private String cidade;

    @Column
    private String servico;

}
