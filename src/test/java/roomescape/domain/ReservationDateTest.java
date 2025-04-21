package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReservationDateTest {

    @Test
    void 예약_날짜는_null일_수_없다() {
        // when & then
        assertThatThrownBy(() -> new ReservationDate(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 날짜는 null일 수 없습니다.");
    }
}
