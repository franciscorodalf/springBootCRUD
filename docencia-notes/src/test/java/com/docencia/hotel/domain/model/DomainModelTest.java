package com.docencia.hotel.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DomainModelTest {

    @Test
    void guestBeanContractsWork() {
        Guest guest = new Guest()
                .id("G-1")
                .nombre("Ana")
                .email("ana@example.com")
                .telefono("600111222");

        Guest sameGuest = new Guest("G-1", "Ana", "ana@example.com", "600111222");

        assertThat(guest.getNombre()).isEqualTo("Ana");
        assertThat(guest).isEqualTo(sameGuest);
        assertThat(guest).hasSameHashCodeAs(sameGuest);
        assertThat(guest.toString()).contains("Ana");
    }

    @Test
    void hotelHandlesRoomsAndBuilderMethods() {
        Hotel hotel = new Hotel()
                .id("H-1")
                .nombre("Costa Azul")
                .direccion("Av. Playa 5");

        Room room = new Room();
        room.setId("R-1");
        room.setHotel(hotel);
        hotel.setRooms(List.of(room));

        assertThat(hotel.getRooms()).hasSize(1);
        assertThat(new Hotel("H-1", "Costa Azul", "Av. Playa 5")).isEqualTo(hotel);
    }

    @Test
    void roomBeanCoversAllProperties() {
        Hotel hotel = new Hotel("H-2", "Montaña", "Calle Pico 3");
        List<Booking> bookings = new ArrayList<>();
        Room room = new Room()
                .id("R-2")
                .numeroHabitacion(200)
                .tipo("Suite")
                .precio(180.0)
                .hotel(hotel)
                .bookings(bookings);

        assertThat(room.getNumeroHabitacion()).isEqualTo(200);
        assertThat(room.getBookings()).isSameAs(bookings);
        assertThat(room).isEqualTo(new Room("R-2", 0, null, 0.0, hotel, bookings));
        assertThat(room.toString()).contains("Suite");
    }

    @Test
    void bookingFluentSettersAndEquality() {
        Hotel hotel = new Hotel("H-3", "Ciudad", "Centro 1");
        Room room = new Room("R-3", 101, "Doble", 95.0, hotel, new ArrayList<>());
        Guest guest = new Guest("G-3", "Luis", "luis@example.com", "600222333");

        Booking booking = new Booking()
                .id("B-1")
                .checkIn(LocalDate.of(2025, 5, 1))
                .checkOut(LocalDate.of(2025, 5, 5))
                .room(room)
                .guest(guest);

        Booking sameBooking = new Booking("B-1",
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 5),
                room,
                guest);

        assertThat(booking).isEqualTo(sameBooking);
        assertThat(booking).hasSameHashCodeAs(sameBooking);
        assertThat(booking.toString()).contains("B-1");
    }
}
