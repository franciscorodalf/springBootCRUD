package com.docencia.hotel;

import com.docencia.hotel.domain.model.*;
import com.docencia.hotel.persistence.jpa.*;

import jakarta.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    private final HotelJpaRepository hotelRepo;
    private final RoomJpaRepository roomRepo;
    private final GuestJpaRepository guestRepo;
    private final BookingJpaRepository bookingRepo;

    public DataLoader(HotelJpaRepository hotelRepo, RoomJpaRepository roomRepo,
            GuestJpaRepository guestRepo, BookingJpaRepository bookingRepo) {
        this.hotelRepo = hotelRepo;
        this.roomRepo = roomRepo;
        this.guestRepo = guestRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {

        Hotel hotel = new Hotel("H1", "Hotel Puerto", "Av. del Mar 123", new ArrayList<>());
        hotelRepo.guardar(hotel);

        Room room = new Room("R1", 101, "Suite", 95.0, hotel, new ArrayList<>());
        roomRepo.guardar(room);

        Guest guest = new Guest("G1", "Carlos", "carlos@example.com", "600123456");
        guestRepo.guardar(guest);

       Booking booking = new Booking("B1", LocalDate.of(2025, 11, 5),
                LocalDate.of(2025, 11, 10), room, guest);
        bookingRepo.guardar(booking);
    }
}
