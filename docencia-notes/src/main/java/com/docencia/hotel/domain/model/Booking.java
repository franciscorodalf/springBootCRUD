package com.docencia.hotel.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private String id;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;


    public Booking() {
    }

    public Booking(String id, LocalDate checkIn, LocalDate checkOut, Room room, Guest guest) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
        this.guest = guest;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCheckIn() {
        return this.checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return this.checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Booking id(String id) {
        setId(id);
        return this;
    }

    public Booking checkIn(LocalDate checkIn) {
        setCheckIn(checkIn);
        return this;
    }

    public Booking checkOut(LocalDate checkOut) {
        setCheckOut(checkOut);
        return this;
    }

    public Booking room(Room room) {
        setRoom(room);
        return this;
    }

    public Booking guest(Guest guest) {
        setGuest(guest);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(checkIn, booking.checkIn) && Objects.equals(checkOut, booking.checkOut) && Objects.equals(room, booking.room) && Objects.equals(guest, booking.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkIn, checkOut, room, guest);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", checkIn='" + getCheckIn() + "'" +
            ", checkOut='" + getCheckOut() + "'" +
            ", room='" + getRoom() + "'" +
            ", guest='" + getGuest() + "'" +
            "}";
    }
   

}
