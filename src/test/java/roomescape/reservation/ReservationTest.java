package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.time.ReservationTime;

class ReservationTest {
    @DisplayName("id가_같은지_여부를_반환한다")
    @Test
    void isIdEquals() {
        // given
        Reservation reservation = new Reservation(1L, "레오", LocalDate.now(), new ReservationTime(1L, LocalTime.now()));

        // when
        boolean result = reservation.isIdEquals(1L);

        // then
        assertThat(result).isTrue();
    }
}
