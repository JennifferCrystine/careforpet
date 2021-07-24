package com.gpms.petcare.repository;

import com.gpms.petcare.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Long> {

}
