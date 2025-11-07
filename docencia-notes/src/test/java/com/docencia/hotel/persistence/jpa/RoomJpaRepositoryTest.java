package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.model.Room;
import com.docencia.hotel.domain.repository.Interface.IHotelJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IRoomJpaRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomJpaRepositoryTest {

    @Autowired
    private IRoomJpaRepository roomRepository;

    @Autowired
    private IHotelJpaRepository hotelRepository;

    @Test
    void findByHotelIdReturnsRoomsForHotel() {
        Hotel hotel = hotelRepository.save(new Hotel("HTL-10", "Costa Azul", "Paseo Playa 2"));
        Room suite = new Room("RM-1", 201, "Suite", 150.0, hotel, new ArrayList<>());
        Room doubleRoom = new Room("RM-2", 202, "Doble", 120.0, hotel, new ArrayList<>());
        roomRepository.saveAll(List.of(suite, doubleRoom));

        List<Room> result = roomRepository.findByHotelId("HTL-10");

        assertThat(result)
                .extracting(Room::getId)
                .containsExactlyInAnyOrder("RM-1", "RM-2");
    }

    @Test
    void findByTipoAndPrecioFiltersCorrectly() {
        Hotel hotel = hotelRepository.save(new Hotel("HTL-11", "Costa Verde", "Av. Bosque 1"));
        Room suite = new Room("RM-3", 301, "Suite", 200.0, hotel, new ArrayList<>());
        Room budgetSuite = new Room("RM-4", 302, "Suite", 95.0, hotel, new ArrayList<>());
        Room doubleRoom = new Room("RM-5", 303, "Doble", 80.0, hotel, new ArrayList<>());
        roomRepository.saveAll(List.of(suite, budgetSuite, doubleRoom));

        List<Room> suites = roomRepository.findByTipo("Suite");
        List<Room> cheapRooms = roomRepository.findByPrecioLessThanEqual(100.0);

        assertThat(suites)
                .extracting(Room::getId)
                .containsExactlyInAnyOrder("RM-3", "RM-4");

        assertThat(cheapRooms)
                .extracting(Room::getId)
                .containsExactlyInAnyOrder("RM-4", "RM-5");
    }
}
