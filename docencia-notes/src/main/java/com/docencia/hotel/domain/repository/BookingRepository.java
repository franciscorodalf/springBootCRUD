package com.docencia.hotel.domain.repository;

import java.time.LocalDate;
import java.util.List;
import com.docencia.hotel.domain.model.Booking;

public interface BookingRepository {

    boolean existeID(String id);

    Booking encontrarPorID(String id);

    List<Booking> encontrarTodos();

    void guardar(Booking booking);

    void eliminarPorId(String id);

    List<Booking> encontrarPorRoomYRangoFechas(String roomId, LocalDate inicio, LocalDate fin);
}
