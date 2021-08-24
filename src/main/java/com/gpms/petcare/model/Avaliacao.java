package com.gpms.petcare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "avaliacao")
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario avaliador;

    @ManyToOne
    private Profissional avaliado;

    private Double nota;


}
