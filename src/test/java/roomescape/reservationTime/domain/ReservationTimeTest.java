package roomescape.reservationTime.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class ReservationTimeTest {

    @Test
    @DisplayName("예약 시간이 같으면 true를 반환한다")
    void isSameTime_true() {
        // given
        int dummyHour = 11;
        int dummyMinute = 13;

        LocalTime inputStartAt = LocalTime.of(dummyHour, dummyMinute);
        LocalTime reservationStartAt = LocalTime.of(dummyHour, dummyMinute);

        ReservationTime inputReservationTime = new ReservationTime(inputStartAt);
        ReservationTime reservationTime = new ReservationTime(reservationStartAt);

        // when
        boolean actual = reservationTime.isSameTime(inputReservationTime);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
