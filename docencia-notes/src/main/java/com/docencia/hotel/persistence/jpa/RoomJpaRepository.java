package com.docencia.hotel.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.docencia.hotel.domain.model.Room;
import com.docencia.hotel.domain.repository.RoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RoomJpaRepository extends AbstractJpaRepository<Room, String> implements RoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public RoomJpaRepository() {
        super(Room.class);
    }

    @Override
    public boolean existeID(String id) {
        return existePorId(id);
    }

    @Override
    public Room encontrarPorID(String id) {
        return encontrarPorId(id);
    }

    @Override
    public List<Room> encontrarTodos() {
        return encontrarTodos();
    }

    @Override
    public void guardar(Room room) {
        entityManager.merge(room);
    }

    @Override
    public void eliminarPorId(String id) {
        super.eliminarPorId(id);
    }

    @Override
    public List<Room> encontrarPorHotelId(String hotelId) {
        return entityManager.createQuery(
                "SELECT r FROM Room r WHERE r.hotel.id = :hotelId", Room.class)
                .setParameter("hotelId", hotelId)
                .getResultList();
    }
}
