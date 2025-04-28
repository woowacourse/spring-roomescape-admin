package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 예약_일시가_null일_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(1L, "name1", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 일시가 null일 수 없습니다.");
    }

    @Test
    void 이름이_null일_경우_예외가_발생한다() {
        ReservationDateTime reservationDateTime = ReservationDateTime.of(LocalDate.of(2025, 1, 1),
                new ReservationTime(null, LocalTime.of(9, 0)));
        assertThatThrownBy(() -> new Reservation(1L, null, reservationDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름이 빈 값일 수 없습니다.");
    }

    @Test
    void 이름이_공백일_경우_예외가_발생한다() {
        ReservationDateTime reservationDateTime = ReservationDateTime.of(LocalDate.of(2025, 1, 1),
                new ReservationTime(null, LocalTime.of(9, 0)));
        assertThatThrownBy(() -> new Reservation(1L, "", reservationDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름이 빈 값일 수 없습니다.");
    }

    @Test
    void 이름이_10자_초과일_경우_예외가_발생한다() {
        ReservationDateTime reservationDateTime = ReservationDateTime.of(LocalDate.of(2025, 1, 1),
                new ReservationTime(null, LocalTime.of(9, 0)));
        assertThatThrownBy(() -> new Reservation(1L, "12345678901", reservationDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 10자를 초과할 수 없습니다.");
    }
}