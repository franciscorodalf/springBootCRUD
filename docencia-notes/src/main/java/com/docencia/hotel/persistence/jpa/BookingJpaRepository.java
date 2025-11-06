package com.docencia.hotel.persistence.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.docencia.hotel.domain.model.Booking;
import com.docencia.hotel.domain.repository.BookingRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class BookingJpaRepository extends AbstractJpaRepository<Booking, String> implements BookingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public BookingJpaRepository() {
        super(Booking.class);
    }

    @Override
    public boolean existeID(String id) {
        return existePorId(id);
    }

    @Override
    public Booking encontrarPorID(String id) {
        return encontrarPorId(id);
    }

    @Override
    public List<Booking> encontrarTodos() {
        return encontrarTodos();
    }

    @Override
    public void guardar(Booking booking) {
        entityManager.merge(booking);
    }

    @Override
    public void eliminarPorId(String id) {
        super.eliminarPorId(id);
    }

    @Override
    public List<Booking> encontrarPorRoomYRangoFechas(String roomId, LocalDate inicio, LocalDate fin) {
        return entityManager.createQuery(
                "SELECT b FROM Booking b " +
                        "WHERE b.room.id = :roomId " +
                        "AND b.checkIn <= :fin " +
                        "AND b.checkOut >= :inicio",
                Booking.class)
                .setParameter("roomId", roomId)
                .setParameter("inicio", inicio)
                .setParameter("fin", fin)
                .getResultList();
    }
}
