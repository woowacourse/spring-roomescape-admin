package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("Reservation에 id를 삽입한 객체를 만들 수 있다.")
    void withId() {
        // given
        Reservation reservation = new Reservation(null, "moko", LocalDate.now(), LocalTime.now());

        // when
        Reservation expected = reservation.withId(10L);

        // then
        assertThat(expected.id()).isEqualTo(10L);
    }
}
