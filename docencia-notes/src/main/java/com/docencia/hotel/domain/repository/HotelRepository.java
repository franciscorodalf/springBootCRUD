package com.docencia.hotel.domain.repository;

import java.util.List;

import com.docencia.hotel.domain.model.Hotel;

public interface HotelRepository {
    /**
     * Comprueba si existe un hotel con una id dada
     * @param id del hotel a buscar
     * @return true si existe, false si no existe
     */
    boolean existeID(String id);

    /**
     * Busca un hotel por su id
     * @param id del hotel que se quiere buscar
     * @return el hotel si existe
     */
    Hotel encontrarPorID(String id);

    /**
     * Devuelve una lista de todos los hoteles
     * @return una lista de los hoteles
     */
    List<Hotel> encontrarTodos();

    /**
     * Inserta o actualiza un hotel
     * @param hotel el que se quiere guardar o actualizar
     */
    void guardar(Hotel hotel);

    /**
     * Borrar un hotel por su id
     * @param id del hotel al que se quiera eliminar
     */
    void eliminarPorId(String id);
}
