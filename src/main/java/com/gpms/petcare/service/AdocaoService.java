package com.gpms.petcare.service;

import com.gpms.petcare.model.Adocao;
import com.gpms.petcare.model.Pet;
import com.gpms.petcare.model.Usuario;
import com.gpms.petcare.repository.AdocaoRepository;
import com.gpms.petcare.repository.PetRepository;
import com.gpms.petcare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PetRepository petRepository;

    public Pet cadastraNovoPet(String nome, String raça) throws Exception {
        if (nome == null){
          throw new Exception("nome nao pode ser nulo");
        }
        else {
        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setRaça(raça);
        Pet novoPet = petRepository.save(pet);
        return novoPet ;
        }
    }

    public Adocao adotaPet(Long id, String usuarioLogado) throws Exception {
        Adocao adotado = new Adocao();
        try {
            Usuario usuario = usuarioRepository.findByEmail(usuarioLogado).orElseThrow();
            Pet pet = petRepository.getById(id);
            Adocao adocao = new Adocao(pet, usuario);
             adotado = adocaoRepository.save(adocao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adotado;
    }
 }

