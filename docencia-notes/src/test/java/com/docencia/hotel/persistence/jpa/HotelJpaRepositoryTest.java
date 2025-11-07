package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.docencia.hotel.domain.model.Hotel;
import com.docencia.hotel.domain.repository.Interface.IHotelJpaRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HotelJpaRepositoryTest {

    @Autowired
    private IHotelJpaRepository hotelRepository;

    @Test
    void findByNombreReturnsMatchingHotels() {
        Hotel atlantic = new Hotel("HTL-1", "Atlántico", "Av. Oceano 10");
        Hotel mountain = new Hotel("HTL-2", "Montaña", "Calle Nieve 5");
        hotelRepository.saveAll(List.of(atlantic, mountain));

        List<Hotel> result = hotelRepository.findByNombre("Atlántico");

        assertThat(result)
                .hasSize(1)
                .first()
                .extracting(Hotel::getDireccion)
                .isEqualTo("Av. Oceano 10");
    }

    @Test
    void findByDireccionContainingReturnsPartialMatches() {
        Hotel atlantic = new Hotel("HTL-3", "Atlántico", "Avenida Mar 123");
        Hotel dunes = new Hotel("HTL-4", "Dunas", "Carretera Interior 9");
        hotelRepository.saveAll(List.of(atlantic, dunes));

        List<Hotel> result = hotelRepository.findByDireccionContaining("Mar");

        assertThat(result)
                .extracting(Hotel::getId)
                .containsExactly("HTL-3");
    }
}
