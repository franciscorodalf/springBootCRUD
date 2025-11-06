package com.docencia.hotel.domain.repository.Interface;

import com.docencia.hotel.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingJpaRepository extends JpaRepository<Booking, String> {

    List<Booking> findByRoomId(String roomId);

    List<Booking> findByGuestId(String guestId);

    List<Booking> findByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            String roomId, LocalDate fin, LocalDate inicio);

}
