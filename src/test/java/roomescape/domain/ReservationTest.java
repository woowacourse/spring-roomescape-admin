package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.NonEmptyNameException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;

class ReservationTest {

    @Test
    void 예약자명이_없거나_비어있는_예약은_생성할_수_없다() {
        assertThatCode(() -> Reservation.from("", LocalDate.MIN, 1))
                .isInstanceOf(NonEmptyNameException.class)
                .hasMessage("이름은 비어있을 수 없습니다");

        assertThatCode(() -> Reservation.from(null, LocalDate.MIN, 1))
                .isInstanceOf(NonEmptyNameException.class)
                .hasMessage("이름은 비어있을 수 없습니다");
    }

    @Test
    void 예약날짜가_없는_예약은_생성할_수_없다() {
        assertThatCode(() -> Reservation.from("조앤", null, 1))
                .isInstanceOf(NonEmptyNameException.class)
                .hasMessage("예약날짜는 비어있을 수 없습니다");
    }
}
