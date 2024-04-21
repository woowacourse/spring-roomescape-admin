package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("id가 같으면 같은 hash 값을 갖고 동일한 객체이다.")
    void hash_equals_override_test() {
        Reservation reservation1 = new Reservation(
                1L, "재즈", LocalDate.of(1999, 11, 30), new ReservationTime(1L, LocalTime.now()));
        Reservation reservation2 = new Reservation(
                1L, "안돌", LocalDate.now(), new ReservationTime(1L, LocalTime.now()));

        assertAll(
                () -> assertThat(reservation1.hashCode()).isEqualTo(reservation2.hashCode()),
                () -> assertThat(reservation1).isEqualTo(reservation2)
        );
    }
}
