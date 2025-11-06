package com.docencia.hotel.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.repository.HotelRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class HotelJpaRepository extends AbstractJpaRepository<Hotel, String> implements HotelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public HotelJpaRepository() {
        super(Hotel.class);
    }

    @Override
    public boolean existeID(String id) {
        return existePorId(id);
    }

    @Override
    public Hotel encontrarPorID(String id) {
        return encontrarPorId(id);
    }

    @Override
    public List<Hotel> encontrarTodos() {
        return encontrarTodos();
    }

    @Override
    public void guardar(Hotel hotel) {
        entityManager.merge(hotel);
    }

    @Override
    public void eliminarPorId(String id) {
        super.eliminarPorId(id);
    }
}
