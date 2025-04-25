package roomescape.reservationtime.unit.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservationtime.domain.ReservationTime;

class ReservationTimeTest {

    @Test
    @DisplayName("시작 시간이 같으면 True를 반환한다.")
    void isStartAtEqualTo_True() {
        // given
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.of(10, 10));
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.of(10, 10));

        // when
        boolean result = reservationTime1.isStartAtEqualTo(reservationTime2);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("시작 시간이 다르면 False를 반환한다.")
    void isStartAtEqualTo_False() {
        // given
        ReservationTime reservationTime1 = new ReservationTime(1L, LocalTime.of(10, 10));
        ReservationTime reservationTime2 = new ReservationTime(2L, LocalTime.of(9, 10));

        // when
        boolean result = reservationTime1.isStartAtEqualTo(reservationTime2);

        // then
        assertThat(result).isFalse();
    }
}
