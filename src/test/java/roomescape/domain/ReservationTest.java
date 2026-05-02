package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 예약자_이름이_null이면_예외_테스트() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation(null,
                LocalDate.of(2023, 8, 5),
                time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약자_이름이_빈_문자열이면_예외_테스트() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation("",
                LocalDate.of(2023, 8, 5),
                time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약자_이름이_공백이면_예외_테스트() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation("   ",
                LocalDate.of(2023, 8, 5),
                time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_날짜가_null이면_예외_테스트() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation("브라운", null, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_시간이_null이면_예외_테스트() {
        assertThatThrownBy(() -> new Reservation(
                "브라운",
                LocalDate.of(2023, 8, 5),
                null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
