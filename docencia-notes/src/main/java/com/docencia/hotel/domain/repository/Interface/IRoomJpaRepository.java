package com.docencia.hotel.domain.repository.Interface;

import com.docencia.hotel.domain.model.Room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomJpaRepository extends JpaRepository<Room, String> {

    List<Room> findByHotelId(String hotelId);

    List<Room> findByTipo(String tipo);

    List<Room> findByPrecioLessThanEqual(Double precio);
}
