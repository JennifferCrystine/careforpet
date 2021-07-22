package com.gpms.petcare.model;

import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "adocao")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Adocao(Pet pet, Usuario usuario) {
        this.pet = pet;
        this.usuario = usuario;
    }

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Usuario usuario;
}
