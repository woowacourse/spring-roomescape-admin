package roomescape.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReservationTest {

    @Test
    void 이름_날짜_시간으로_예약이_정상적으로_생성된다() {
        final String name = "시소";
        final LocalDate date = LocalDate.of(2025, 4, 23);
        final LocalTime time = LocalTime.of(12, 30);

        assertThatNoException()
                .isThrownBy(() -> new Reservation(name, date, time));
    }

    @Test
    void ID_이름_날짜_시간으로_예약이_정상적으로_생성된다() {
        final Long id = 1L;
        final String name = "시소";
        final LocalDate date = LocalDate.of(2025, 4, 23);
        final LocalTime time = LocalTime.of(12, 30);

        assertThatNoException()
                .isThrownBy(() -> new Reservation(id, name, date, time));
    }

    @Test
    void ID와_예약으로_정상적으로_생성된다() {
        final Long id = 1L;
        final Reservation baseReservation = new Reservation(
                "시소",
                LocalDate.of(2025, 4, 23),
                LocalTime.of(12, 30)
        );

        assertThatNoException()
                .isThrownBy(() -> new Reservation(id, baseReservation));
    }

    @Test
    void 이름이_비어있다면_예외가_발생한다() {
        final Long id = 1L;
        final String name = "";
        final LocalDate date = LocalDate.of(2025, 4, 23);
        final LocalTime time = LocalTime.of(12, 30);

        assertThatThrownBy(() -> new Reservation(id, name, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_NULL이라면_예외가_발생한다() {
        final String name = null;
        final LocalDate date = LocalDate.of(2025, 4, 23);
        final LocalTime time = LocalTime.of(12, 30);

        assertThatThrownBy(() -> new Reservation(name, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 날짜가_NULL이라면_예외가_발생한다() {
        final String name = "시소";
        final LocalDate date = null;
        final LocalTime time = LocalTime.of(12, 30);

        assertThatThrownBy(() -> new Reservation(name, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시간이_NULL이라면_예외가_발생한다() {
        final String name = "시소";
        final LocalDate date = LocalDate.of(2025, 4, 23);
        final LocalTime time = null;

        assertThatThrownBy(() -> new Reservation(name, date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
