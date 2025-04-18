package roomescape.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.Reservation;

class ReservationTest {
    @DisplayName("id가_같은지_여부를_반환한다")
    @Test
    void isIdEquals() {
        // given
        Reservation reservation = new Reservation(1L, "레오", LocalDateTime.now());

        // when
        boolean result = reservation.isIdEquals(1L);

        // then
        assertThat(result).isTrue();
    }
}
