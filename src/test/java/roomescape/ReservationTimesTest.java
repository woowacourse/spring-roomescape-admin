package roomescape;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;

class ReservationTimesTest {
    @Test
    void constructor() {
        // when
        ReservationTimes reservationTimes = new ReservationTimes();

        // then
        assertAll(
                () -> Assertions.assertThat(reservationTimes.getReservationTimes()).hasSize(0),
                () -> Assertions.assertThat(reservationTimes).isInstanceOf(ReservationTimes.class)
        );
    }

    @Test
    void add() {
        // given
        ReservationTimes reservationTimes = new ReservationTimes();
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());

        // when
        reservationTimes.add(reservationTime);

        // then
        Assertions.assertThat(reservationTimes.getReservationTimes()).hasSize(1);
    }

    @Test
    void remove() {
        // given
        ReservationTimes reservationTimes = new ReservationTimes();
        reservationTimes.add(new ReservationTime(1L, LocalTime.now()));

        // when
        reservationTimes.remove(1L);

        // then
        Assertions.assertThat(reservationTimes.getReservationTimes()).isEmpty();
    }

    @DisplayName("일치하는 ID의 시간을 찾을 수 없을 때 .remove()의 예외 발생 테스트")
    @Test
    void removeNotExistId() {
        // given
        ReservationTimes reservationTimes = new ReservationTimes();
        reservationTimes.add(new ReservationTime(0L, LocalTime.now()));

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                () -> reservationTimes.remove(1L));
    }
}
