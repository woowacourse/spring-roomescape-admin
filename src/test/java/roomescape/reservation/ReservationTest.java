package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @DisplayName("이미 id가 존재한다면, 예외가 발생한다.")
    @Test
    void writeId1() {
        // given
        final Reservation reservation = new Reservation(1L,
                "", LocalDate.of(2025, 04, 19), 1L);

        // when & then
        assertThatCode(() -> {
            reservation.writeId(2);
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("id를 기록한다.")
    @Test
    void writeId3() {
        // given
        final Reservation reservation = new Reservation(null,
                "", LocalDate.of(2025, 04, 19), 1L);

        // when
        final Reservation actual = reservation.writeId(1);

        // then
        assertThat(actual).isEqualTo(new Reservation(1L,
                "", LocalDate.of(2025, 04, 19), 1L));
    }
}
