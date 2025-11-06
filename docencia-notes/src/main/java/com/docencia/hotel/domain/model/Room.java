package com.docencia.hotel.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room {

    @Id
    String id;
    @Column(nullable = false)
    int numeroHabitacion;
    @Column(nullable = false)
    String tipo;
    @Column(nullable = false)
    double precio;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings = new ArrayList<>();

    public Room() {
    }

    public Room(String id, int numeroHabitacion, String tipo, double precio, Hotel hotel, List<Booking> bookings) {
        this.id = id;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.precio = precio;
        this.hotel = hotel;
        this.bookings = bookings;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumeroHabitacion() {
        return this.numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Room id(String id) {
        setId(id);
        return this;
    }

    public Room numeroHabitacion(int numeroHabitacion) {
        setNumeroHabitacion(numeroHabitacion);
        return this;
    }

    public Room tipo(String tipo) {
        setTipo(tipo);
        return this;
    }

    public Room precio(double precio) {
        setPrecio(precio);
        return this;
    }

    public Room hotel(Hotel hotel) {
        setHotel(hotel);
        return this;
    }

    public Room bookings(List<Booking> bookings) {
        setBookings(bookings);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", numeroHabitacion='" + getNumeroHabitacion() + "'" +
                ", tipo='" + getTipo() + "'" +
                ", precio='" + getPrecio() + "'" +
                ", hotel='" + getHotel() + "'" +
                ", bookings='" + getBookings() + "'" +
                "}";
    }
}
