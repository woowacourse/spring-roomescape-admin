package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationsTest {
    Person person;
    ReservationTime reservationTime;
    Reservations reservations;

    @BeforeEach
    void makeReservation() {
        person = new Person(1, "이름");
        reservationTime = new ReservationTime(LocalDateTime.of(2024, 2, 25, 10, 0));
        reservations = new Reservations();
        reservations.save(person, reservationTime);
    }

    @Test
    void saveTest() {
        Reservation answer = new Reservation(1, person, reservationTime);
        assertThat(reservations.getReservations()).isEqualTo(List.of(answer));
    }

    @Test
    void deleteByIdTest() {
        assertThatCode(() -> reservations.deleteById(1)).doesNotThrowAnyException();
    }

    @Test
    void deleteByIdExceptionTest() {
        assertThatThrownBy(() -> reservations.deleteById(2)).isInstanceOf(IllegalArgumentException.class);
    }
}