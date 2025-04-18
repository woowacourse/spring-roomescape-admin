package roomescape.domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void sameIdTest() {
        Person person = new Person(1, "이름");
        ReservationTime reservationTime = new ReservationTime(LocalDateTime.of(2024, 2, 25, 10, 0));
        Reservation reservation = new Reservation(1, person, reservationTime);

        assertAll(
                () -> Assertions.assertThat(reservation.sameId(1)).isTrue(),
                () -> Assertions.assertThat(reservation.sameId(2)).isFalse()
        );
    }
}