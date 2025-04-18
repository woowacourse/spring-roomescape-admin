package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("같은 날짜, 시간 확인 테스트")
    void isSameDateTimeTest() {
        Reservation reservation1 = new Reservation(
                1L, "brown", LocalDate.of(2024, 12, 12), LocalTime.of(9, 3)
        );

        Reservation reservation2 = new Reservation(
                1L, "brown", LocalDate.of(2024, 12, 12), LocalTime.of(9, 3)
        );

        Reservation reservation3 = new Reservation(
                1L, "brown", LocalDate.of(2024, 12, 11), LocalTime.of(9, 3)
        );

        assertThat(reservation1.isSameDateTime(reservation2)).isTrue();
        assertThat(reservation1.isSameDateTime(reservation3)).isFalse();
    }

}
