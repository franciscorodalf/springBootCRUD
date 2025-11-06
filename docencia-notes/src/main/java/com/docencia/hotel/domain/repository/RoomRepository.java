package com.docencia.hotel.domain.repository;

import java.util.List;
import com.docencia.hotel.domain.model.Room;

public interface RoomRepository {
    boolean existeID(String id);

    Room encontrarPorID(String id);

    List<Room> encontrarTodos();

    void guardar(Room room);

    void eliminarPorId(String id);

    List<Room> encontrarPorHotelId(String hotelId);
}
