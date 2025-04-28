package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ReservationDateTest {

    @Test
    void 예약_날짜는_null일_수_없다() {
        // given
        LocalDate date = null;

        // when & then
        assertThatThrownBy(() -> new ReservationDate(date))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 날짜는 null일 수 없습니다.");
    }
}
