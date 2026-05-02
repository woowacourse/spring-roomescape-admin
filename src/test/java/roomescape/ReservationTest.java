package roomescape;

import org.junit.jupiter.api.Test;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTest {
    @Test
    void 예약자명이_null이면_예외를_던진다() {
        ReservationTime reservationTime = ReservationTime.create(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> Reservation.create(
                1L,
                null,
                LocalDate.of(2026, 5, 1),
                reservationTime)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약자명이_빈_문자열이면_예외를_던진다() {
        ReservationTime reservationTime = ReservationTime.create(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> Reservation.create(
                1L,
                "  ",
                LocalDate.of(2026, 5, 1),
                reservationTime)
        ).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 예약_날짜가_null이면_예외를_던진다() {
        ReservationTime reservationTime = ReservationTime.create(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> Reservation.create(
                1L,
                "브라운",
                null,
                reservationTime)
        ).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 예약_시간이_null이면_예외를_던진다() {
        assertThatThrownBy(() -> Reservation.create(
                1L,
                null,
                LocalDate.of(2026, 5, 1),
                null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
