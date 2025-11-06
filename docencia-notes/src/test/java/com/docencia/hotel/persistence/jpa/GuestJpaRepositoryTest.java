package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.docencia.hotel.domain.model.Guest;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:sqlite:file:memdb-guest?mode=memory&cache=shared",
        "spring.datasource.driver-class-name=org.sqlite.JDBC",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.SQLiteDialect"
})
@Import(GuestJpaRepository.class)
class GuestJpaRepositoryTest {

    @Autowired
    private GuestJpaRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void guardarShouldPersistGuest() {
        Guest guest = createGuest("G1");

        repository.guardar(guest);
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("G1")).isTrue();
        Guest persisted = repository.encontrarPorID("G1");
        assertThat(persisted).isNotNull();
        assertThat(persisted.getNombre()).isEqualTo("Guest Test");
        assertThat(persisted.getEmail()).isEqualTo("guest@test.com");
        assertThat(persisted.getTelefono()).isEqualTo("555-1234");
    }

    @Test
    void eliminarPorIdShouldRemoveGuest() {
        repository.guardar(createGuest("G2"));
        entityManager.flush();

        repository.eliminarPorId("G2");
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("G2")).isFalse();
        assertThat(repository.encontrarPorID("G2")).isNull();
    }

    private static Guest createGuest(String id) {
        Guest guest = new Guest();
        guest.setId(id);
        guest.setNombre("Guest Test");
        guest.setEmail("guest@test.com");
        guest.setTelefono("555-1234");
        return guest;
    }
}
