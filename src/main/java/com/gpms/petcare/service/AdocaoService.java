package com.gpms.petcare.service;

import com.gpms.petcare.model.Adocao;
import com.gpms.petcare.model.Pet;
import com.gpms.petcare.model.Usuario;
import com.gpms.petcare.repository.AdocaoRepository;
import com.gpms.petcare.repository.PetRepository;
import com.gpms.petcare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PetRepository petRepository;

    public Pet cadastraNovoPet(String nome, String raca) throws Exception {
        if (nome == null){
          throw new Exception("nome nao pode ser nulo");
        }
        else {
        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setRaca(raca);
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

    public List<Pet> listaPet() {
        List<Pet> pet = new ArrayList<>();
        try {
          pet = petRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pet;
    }

    public List<Adocao> listaAdotado() {
        List<Adocao> adotados = new ArrayList<>();
        try {
            adotados = adocaoRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return adotados;
    }

    public Pet buscaPetPorId(Long id) {
        return petRepository.getById(id);
    }
}

