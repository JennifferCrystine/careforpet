package com.gpms.petcare.repository;

import com.gpms.petcare.model.Passeio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasseioRepository extends JpaRepository<Passeio, Long> {


}
