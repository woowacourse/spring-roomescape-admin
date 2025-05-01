package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservationtime.ReservationTime;

class ReservationTest {

    @DisplayName("예약자명은 null이거나 빈 값이면 예외를 반환한다")
    @Test
    void validateName() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());

        // when // then
        assertThatCode(() -> new Reservation(1L, null, LocalDate.now(), reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("예약 날짜는 null이면 예외를 반환한다")
    @Test
    void validateDate() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now());

        // when // then
        assertThatCode(() -> new Reservation(1L, "검프", null, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("예약 시간은 null이면 예외를 반환한다")
    @Test
    void validateTime() {
        // when // then
        assertThatCode(() -> new Reservation(1L, "검프", LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
