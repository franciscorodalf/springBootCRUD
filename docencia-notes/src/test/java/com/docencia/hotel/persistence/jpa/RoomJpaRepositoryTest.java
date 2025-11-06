package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.model.Room;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:sqlite:file:memdb-room?mode=memory&cache=shared",
        "spring.datasource.driver-class-name=org.sqlite.JDBC",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.SQLiteDialect"
})
@Import(RoomJpaRepository.class)
class RoomJpaRepositoryTest {

    @Autowired
    private RoomJpaRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void guardarShouldPersistRoomWithHotel() {
        Hotel hotel = persistHotel("H1");
        Room room = createRoom("R1", 101, "Suite", 150.0, hotel);

        repository.guardar(room);
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("R1")).isTrue();
        Room persisted = repository.encontrarPorID("R1");
        assertThat(persisted).isNotNull();
        assertThat(persisted.getHotel().getId()).isEqualTo("H1");
        assertThat(persisted.getNumeroHabitacion()).isEqualTo(101);
    }

    @Test
    void encontrarPorHotelIdShouldReturnAllHotelRooms() {
        Hotel hotelA = persistHotel("HA");
        Hotel hotelB = persistHotel("HB");

        repository.guardar(createRoom("A1", 201, "Suite", 210.0, hotelA));
        repository.guardar(createRoom("A2", 202, "Suite", 220.0, hotelA));
        repository.guardar(createRoom("B1", 101, "Standard", 110.0, hotelB));
        entityManager.flush();
        entityManager.clear();

        List<Room> rooms = repository.encontrarPorHotelId("HA");
        assertThat(rooms)
                .extracting(Room::getId)
                .containsExactlyInAnyOrder("A1", "A2");
    }

    @Test
    void eliminarPorIdShouldRemoveRoom() {
        Hotel hotel = persistHotel("H2");
        repository.guardar(createRoom("R2", 301, "Deluxe", 180.0, hotel));
        entityManager.flush();

        repository.eliminarPorId("R2");
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("R2")).isFalse();
        assertThat(repository.encontrarPorID("R2")).isNull();
    }

    private Hotel persistHotel(String id) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setNombre("Hotel " + id);
        hotel.setDireccion("Direccion " + id);
        entityManager.persist(hotel);
        return hotel;
    }

    private static Room createRoom(String id, int numero, String tipo, double precio, Hotel hotel) {
        Room room = new Room();
        room.setId(id);
        room.setNumeroHabitacion(numero);
        room.setTipo(tipo);
        room.setPrecio(precio);
        room.setHotel(hotel);
        return room;
    }
}
