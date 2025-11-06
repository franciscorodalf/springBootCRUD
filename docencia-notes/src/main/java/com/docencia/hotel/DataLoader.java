package com.docencia.hotel;

import com.docencia.hotel.domain.model.*;
import com.docencia.hotel.domain.repository.Interface.IBookingJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IGuestJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IHotelJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IRoomJpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    private final IHotelJpaRepository hotelRepo;
    private final IRoomJpaRepository roomRepo;
    private final IGuestJpaRepository guestRepo;
    private final IBookingJpaRepository bookingRepo;

    public DataLoader(IHotelJpaRepository hotelRepo, IRoomJpaRepository roomRepo,
            IGuestJpaRepository guestRepo,
            IBookingJpaRepository bookingRepo) {
        this.hotelRepo = hotelRepo;
        this.roomRepo = roomRepo;
        this.guestRepo = guestRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Hotel h = new Hotel("H1", "Hotel Atlántico", "Av. Marítima 9", new ArrayList<>());
        hotelRepo.save(h);

        Room r = new Room("R1", 101, "Suite", 95.0, h, new ArrayList<>());
        roomRepo.save(r);

        Guest g = new Guest("G1", "Ana López", "ana@mail.com", "600123456");
        guestRepo.save(g);

        Booking b = new Booking("B1", LocalDate.of(2025, 11, 6),
                LocalDate.of(2025, 11, 9), r, g);
        bookingRepo.save(b);

        System.out.println("Hoteles:");
        hotelRepo.findAll().forEach(System.out::println);

        System.out.println("Habitaciones del hotel H1:");
        roomRepo.findByHotelId("H1").forEach(System.out::println);
    }
}
