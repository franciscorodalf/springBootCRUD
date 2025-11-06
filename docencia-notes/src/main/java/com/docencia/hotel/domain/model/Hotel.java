package com.docencia.hotel.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();

    public Hotel() {
    }

    public Hotel(String id, String nombre, String direccion, List<Room> rooms) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.rooms = rooms;
    }

    public Hotel(String id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Hotel id(String id) {
        setId(id);
        return this;
    }

    public Hotel nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Hotel direccion(String direccion) {
        setDireccion(direccion);
        return this;
    }

    public Hotel rooms(List<Room> rooms) {
        setRooms(rooms);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Hotel)) {
            return false;
        }
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", direccion='" + getDireccion() + "'" +
                ", rooms='" + getRooms() + "'" +
                "}";
    }

}
