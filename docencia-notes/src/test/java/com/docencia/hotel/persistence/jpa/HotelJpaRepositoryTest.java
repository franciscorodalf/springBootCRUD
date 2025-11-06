package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.docencia.hotel.domain.model.Hotel;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:sqlite:file:memdb-hotel?mode=memory&cache=shared",
        "spring.datasource.driver-class-name=org.sqlite.JDBC",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.SQLiteDialect"
})
@Import(HotelJpaRepository.class)
class HotelJpaRepositoryTest {

    @Autowired
    private HotelJpaRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void guardarShouldPersistAndReturnHotel() {
        Hotel hotel = createHotel("H1");

        repository.guardar(hotel);
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("H1")).isTrue();
        Hotel persisted = repository.encontrarPorID("H1");
        assertThat(persisted).isNotNull();
        assertThat(persisted.getNombre()).isEqualTo("Hotel Test");
        assertThat(persisted.getDireccion()).isEqualTo("Direccion 123");
    }

    @Test
    void eliminarPorIdShouldRemoveHotel() {
        Hotel hotel = createHotel("H2");
        repository.guardar(hotel);
        entityManager.flush();

        repository.eliminarPorId("H2");
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("H2")).isFalse();
        assertThat(repository.encontrarPorID("H2")).isNull();
    }

    private static Hotel createHotel(String id) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setNombre("Hotel Test");
        hotel.setDireccion("Direccion 123");
        return hotel;
    }
}
