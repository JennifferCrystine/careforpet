package com.gpms.petcare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "passeio")
public class Passeio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long usuarioId;

    @Column
    private Long profissionalId;

    @Column
    private String data;

}
