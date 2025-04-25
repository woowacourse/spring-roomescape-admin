package roomescape.model;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationDateTimeTest {
    @Test
    @DisplayName("과거 예약 방지 테스트 - 1")
    void test1() {
        ReservationDate date = new ReservationDate("2026-01-01");
        ReservationTime time = new ReservationTime(1L, "10:00");
        assertThatCode(()->new ReservationDateTime(date, time)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("과거 예약 방지 테스트 - 2")
    void test2() {
        ReservationDate date = new ReservationDate("2025-04-25");
        ReservationTime time = new ReservationTime(1L, "10:00");
        assertThatThrownBy(()->new ReservationDateTime(date, time)).hasMessage("과거 예약은 불가능합니다.");
    }

}
