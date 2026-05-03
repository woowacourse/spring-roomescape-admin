package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

class ReservationTest {

    @Test
    void 예약시_이름이_Null이면_예외가_발생한다() {
        // given
        String nullName = null;
        LocalDate date = LocalDate.of(2026, 5, 3);
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        // when & then
        Assertions.assertThatThrownBy(() -> new Reservation(nullName, date, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name은 Null일 수 없습니다.");
    }

    @Test
    void 예약시_이름이_비어있으면_예외가_발생한다() {
        // given
        String blankName = "  ";
        LocalDate date = LocalDate.of(2026, 5, 3);
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        // when & then
        Assertions.assertThatThrownBy(() -> new Reservation(blankName, date, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name은 비어있을 수 없습니다.");
    }

    @Test
    void 예약시_날짜가_Null이면_예외가_발생한다() {
        // given
        String name = "송송";
        LocalDate nullDate = null;
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        // when & then
        Assertions.assertThatThrownBy(() -> new Reservation(name, nullDate, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("date는 Null일 수 없습니다.");
    }

    @Test
    void 예약시_예약시간이_Null이면_예외가_발생한다() {
        // given
        String name = "송송";
        LocalDate date = LocalDate.of(2026, 5, 3);
        ReservationTime nullTime = null;

        // when & then
        Assertions.assertThatThrownBy(() -> new Reservation(name, date, nullTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("reservationTime은 Null일 수 없습니다.");
    }

}
