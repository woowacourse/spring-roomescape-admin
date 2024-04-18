package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("해당 ID를 가진 객체인지 판단한다.")
    @Test
    void hasSameId() {
        Reservation reservation = new Reservation(2L, "aaa", LocalDate.now(), LocalTime.now());
        Long id = 2L;

        assertThat(reservation.hasSameId(id)).isTrue();
    }
}