package com.docencia.hotel.domain.repository;

import java.util.List;
import com.docencia.hotel.domain.model.Guest;

public interface GuestRepository {

    boolean existeID(String id);

    Guest encontrarPorID(String id);

    List<Guest> encontrarTodos();

    void guardar(Guest guest);

    void eliminarPorId(String id);
}
