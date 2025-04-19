package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @DisplayName("이미 id가 존재한다면, 예외가 발생한다.")
    @Test
    void writeId1() {
        // given
        final Reservation reservation = new Reservation(1,
                "", LocalDate.of(2025, 04, 19), LocalTime.of(10, 25, 0));

        // when & then
        assertThatCode(() -> {
            reservation.writeId(2);
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("작성할 id가 비어있다면, 예외가 발생한다.")
    @Test
    void writeId2() {
        // given
        final long emptyId = 0;
        final Reservation reservation = new Reservation(1,
                "", LocalDate.of(2025, 04, 19), LocalTime.of(10, 25, 0));

        // when & then
        assertThatCode(() -> {
            reservation.writeId(emptyId);
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("id를 기록한다.")
    @Test
    void writeId3() {
        // given
        final Reservation reservation = new Reservation(0,
                "", LocalDate.of(2025, 04, 19), LocalTime.of(10, 25, 0));

        // when
        final Reservation actual = reservation.writeId(1);

        // then
        assertThat(actual).isEqualTo(new Reservation(1,
                "", LocalDate.of(2025, 04, 19), LocalTime.of(10, 25, 0)));
    }
}
