package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import com.docencia.hotel.domain.model.Booking;
import com.docencia.hotel.domain.model.Guest;
import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.model.Room;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:sqlite:file:memdb-booking?mode=memory&cache=shared",
        "spring.datasource.driver-class-name=org.sqlite.JDBC",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.community.dialect.SQLiteDialect"
})
@Import(BookingJpaRepository.class)
class BookingJpaRepositoryTest {

    @Autowired
    private BookingJpaRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void guardarShouldPersistBooking() {
        Hotel hotel = persistHotel("H1");
        Room room = persistRoom("R1", hotel);
        Guest guest = persistGuest("G1");
        Booking booking = createBooking("B1", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), room, guest);

        repository.guardar(booking);
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("B1")).isTrue();
        Booking persisted = repository.encontrarPorID("B1");
        assertThat(persisted).isNotNull();
        assertThat(persisted.getRoom().getId()).isEqualTo("R1");
        assertThat(persisted.getGuest().getId()).isEqualTo("G1");
        assertThat(persisted.getCheckIn()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(persisted.getCheckOut()).isEqualTo(LocalDate.of(2024, 1, 5));
    }

    @Test
    void encontrarPorRoomYRangoFechasShouldReturnOverlappingBookings() {
        Hotel hotel = persistHotel("H2");
        Room room = persistRoom("R2", hotel);
        Guest guest = persistGuest("G2");
        Guest otherGuest = persistGuest("G3");
        Booking bookingInside = createBooking("B2", LocalDate.of(2024, 2, 10), LocalDate.of(2024, 2, 15), room, guest);
        Booking bookingOverlapping = createBooking("B3", LocalDate.of(2024, 2, 15), LocalDate.of(2024, 2, 18), room, otherGuest);
        Booking bookingOutside = createBooking("B4", LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 5), room, guest);
        Booking otherRoomBooking = createBooking("B5", LocalDate.of(2024, 2, 10), LocalDate.of(2024, 2, 12),
                persistRoom("R3", hotel), guest);

        repository.guardar(bookingInside);
        repository.guardar(bookingOverlapping);
        repository.guardar(bookingOutside);
        repository.guardar(otherRoomBooking);
        entityManager.flush();
        entityManager.clear();

        List<Booking> bookings = repository.encontrarPorRoomYRangoFechas("R2",
                LocalDate.of(2024, 2, 12),
                LocalDate.of(2024, 2, 16));

        assertThat(bookings)
                .extracting(Booking::getId)
                .containsExactlyInAnyOrder("B2", "B3")
                .doesNotContain("B4", "B5");
    }

    @Test
    void eliminarPorIdShouldRemoveBooking() {
        Hotel hotel = persistHotel("H4");
        Room room = persistRoom("R4", hotel);
        Guest guest = persistGuest("G4");
        repository.guardar(createBooking("B6", LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 3), room, guest));
        entityManager.flush();

        repository.eliminarPorId("B6");
        entityManager.flush();
        entityManager.clear();

        assertThat(repository.existeID("B6")).isFalse();
        assertThat(repository.encontrarPorID("B6")).isNull();
    }

    private Hotel persistHotel(String id) {
        Hotel hotel = new Hotel();
        hotel.setId(id);
        hotel.setNombre("Hotel " + id);
        hotel.setDireccion("Direccion " + id);
        entityManager.persist(hotel);
        return hotel;
    }

    private Room persistRoom(String id, Hotel hotel) {
        Room room = new Room();
        room.setId(id);
        room.setNumeroHabitacion(100);
        room.setTipo("Standard");
        room.setPrecio(120.0);
        room.setHotel(hotel);
        entityManager.persist(room);
        return room;
    }

    private Guest persistGuest(String id) {
        Guest guest = new Guest();
        guest.setId(id);
        guest.setNombre("Guest " + id);
        guest.setEmail("guest" + id + "@test.com");
        guest.setTelefono("555-" + id);
        entityManager.persist(guest);
        return guest;
    }

    private static Booking createBooking(String id, LocalDate checkIn, LocalDate checkOut, Room room, Guest guest) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setRoom(room);
        booking.setGuest(guest);
        return booking;
    }
}
