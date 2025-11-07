package com.docencia.hotel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.docencia.hotel.domain.model.Booking;
import com.docencia.hotel.domain.model.Guest;
import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.model.Room;
import com.docencia.hotel.domain.repository.Interface.IBookingJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IGuestJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IHotelJpaRepository;
import com.docencia.hotel.domain.repository.Interface.IRoomJpaRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataLoaderTest {

    @Mock
    private IHotelJpaRepository hotelRepo;
    @Mock
    private IRoomJpaRepository roomRepo;
    @Mock
    private IGuestJpaRepository guestRepo;
    @Mock
    private IBookingJpaRepository bookingRepo;

    @InjectMocks
    private DataLoader dataLoader;

    @Test
    void runSeedsDemoEntities() {
        when(hotelRepo.findAll()).thenReturn(Collections.emptyList());
        when(roomRepo.findByHotelId("H1")).thenReturn(Collections.emptyList());

        dataLoader.run();

        ArgumentCaptor<Hotel> hotelCaptor = ArgumentCaptor.forClass(Hotel.class);
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        ArgumentCaptor<Guest> guestCaptor = ArgumentCaptor.forClass(Guest.class);
        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);

        verify(hotelRepo).save(hotelCaptor.capture());
        verify(roomRepo).save(roomCaptor.capture());
        verify(guestRepo).save(guestCaptor.capture());
        verify(bookingRepo).save(bookingCaptor.capture());

        assertThat(hotelCaptor.getValue().getNombre()).isEqualTo("Hotel Atlántico");
        assertThat(roomCaptor.getValue().getHotel().getId()).isEqualTo("H1");
        assertThat(guestCaptor.getValue().getEmail()).isEqualTo("ana@mail.com");
        assertThat(bookingCaptor.getValue().getRoom().getId()).isEqualTo("R1");
    }
}
