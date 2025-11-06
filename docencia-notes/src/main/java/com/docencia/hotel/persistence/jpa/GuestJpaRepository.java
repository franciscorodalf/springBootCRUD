package com.docencia.hotel.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.docencia.hotel.domain.model.Guest;
import com.docencia.hotel.domain.repository.GuestRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GuestJpaRepository extends AbstractJpaRepository<Guest, String> implements GuestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public GuestJpaRepository() {
        super(Guest.class);
    }

    @Override
    public boolean existeID(String id) {
        return existePorId(id);
    }

    @Override
    public Guest encontrarPorID(String id) {
        return encontrarPorId(id);
    }

    @Override
    public List<Guest> encontrarTodos() {
        return encontrarTodos();
    }

    @Override
    public void guardar(Guest guest) {
        entityManager.merge(guest);
    }

    @Override
    public void eliminarPorId(String id) {
        super.eliminarPorId(id);
    }
}
