package com.docencia.hotel.domain.repository.Interface;

import com.docencia.hotel.domain.model.Guest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuestJpaRepository extends JpaRepository<Guest, String> {

    Optional<Guest> findByEmail(String email);

    List<Guest> findByNombreContaining(String nombre);

    Optional<Guest> findByTelefono(String telefono);
}
