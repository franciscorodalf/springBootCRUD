package com.docencia.hotel.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.docencia.hotel.domain.model.Guest;
import com.docencia.hotel.domain.repository.Interface.IGuestJpaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GuestJpaRepositoryTest {

    @Autowired
    private IGuestJpaRepository guestRepository;

    @Test
    void findersReturnExpectedGuests() {
        Guest ana = new Guest("GST-1", "Ana Lopez", "ana@mail.com", "600000001");
        Guest bea = new Guest("GST-2", "Beatriz Mar", "bea@mail.com", "600000002");
        guestRepository.saveAll(List.of(ana, bea));

        Optional<Guest> byEmail = guestRepository.findByEmail("ana@mail.com");
        List<Guest> byName = guestRepository.findByNombreContaining("Mar");
        Optional<Guest> byPhone = guestRepository.findByTelefono("600000002");

        assertThat(byEmail)
                .isPresent()
                .get()
                .extracting(Guest::getNombre)
                .isEqualTo("Ana Lopez");

        assertThat(byName)
                .singleElement()
                .extracting(Guest::getId)
                .isEqualTo("GST-2");

        assertThat(byPhone)
                .isPresent()
                .get()
                .extracting(Guest::getEmail)
                .isEqualTo("bea@mail.com");
    }
}
