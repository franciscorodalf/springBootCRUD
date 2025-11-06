package com.docencia.hotel.domain.repository.Interface;

import com.docencia.hotel.domain.model.Hotel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelJpaRepository extends JpaRepository<Hotel, String> {
    List<Hotel> findByNombre(String nombre);

    List<Hotel> findByDireccionContaining(String palabra);
}
