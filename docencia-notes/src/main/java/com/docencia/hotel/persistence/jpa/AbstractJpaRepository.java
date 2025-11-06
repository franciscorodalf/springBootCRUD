package com.docencia.hotel.persistence.jpa;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class AbstractJpaRepository<Entidad, ID> {
    private final Class<Entidad> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected AbstractJpaRepository(Class<Entidad> entityClass) {
        this.entityClass = entityClass;
    }

    public boolean existePorId(ID id) {
        return entityManager.find(entityClass, id) != null;
    }

    public Entidad encontrarPorId(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<Entidad> encontrarTodos() {
        return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }

    public void guardar(Entidad entity) {
        entityManager.merge(entity);
    }

    public void eliminarPorId(ID id) {
        Entidad entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
