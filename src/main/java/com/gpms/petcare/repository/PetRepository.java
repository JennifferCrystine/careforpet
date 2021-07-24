package com.gpms.petcare.repository;

import com.gpms.petcare.model.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Long> {

    List<Pet> findAllByAdotadoFalse();
}
