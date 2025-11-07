package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.docencia.hotel.domain.model.Booking;
import com.docencia.hotel.domain.model.Guest;
import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.model.Room;
import com.docencia.hotel.domain.repository.Interface.IBookingJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IGuestJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IHotelJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IRoomJpaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingJpaRepositoryTest {

    @Autowired
    private IBookingJpaRepository bookingRepository;
    @Autowired
    private IRoomJpaRepository roomRepository;
    @Autowired
    private IHotelJpaRepository hotelRepository;
    @Autowired
    private IGuestJpaRepository guestRepository;

    @Test
    void findByRoomIdReturnsBookingsForRoom() {
        Room room = persistRoom("RM-20");
        Guest guest = guestRepository.save(new Guest("GST-20", "Carmen", "car@mail.com", "600100100"));
        Booking booking = bookingRepository.save(
                new Booking("BKG-1", LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 12), room, guest));

        List<Booking> result = bookingRepository.findByRoomId(room.getId());

        assertThat(result)
                .singleElement()
                .isEqualTo(booking);
    }

    @Test
    void findByGuestIdReturnsBookingsForGuest() {
        Room room = persistRoom("RM-21");
        Guest guest = guestRepository.save(new Guest("GST-21", "Daniel", "dan@mail.com", "600100101"));
        Booking bookingA = bookingRepository.save(
                new Booking("BKG-2", LocalDate.of(2025, 2, 5), LocalDate.of(2025, 2, 8), room, guest));

        List<Booking> result = bookingRepository.findByGuestId(guest.getId());

        assertThat(result)
                .containsExactly(bookingA);
    }

    @Test
    void findByRoomIdAndDatesDetectsOverlaps() {
        Room room = persistRoom("RM-22");
        Guest guest = guestRepository.save(new Guest("GST-22", "Eva", "eva@mail.com", "600100102"));
        bookingRepository.save(
                new Booking("BKG-3", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 5), room, guest));
        Booking overlapping = bookingRepository.save(
                new Booking("BKG-4", LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 15), room, guest));

        List<Booking> result = bookingRepository
                .findByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(room.getId(),
                        LocalDate.of(2025, 3, 12), LocalDate.of(2025, 3, 8));

        assertThat(result)
                .extracting(Booking::getId)
                .containsExactly("BKG-4");
    }

    private Room persistRoom(String roomId) {
        Hotel hotel = hotelRepository.save(new Hotel("HTL-" + roomId, "Test " + roomId, "Direccion " + roomId));
        return roomRepository.save(new Room(roomId, 100, "Suite", 150.0, hotel, new ArrayList<>()));
    }
}
