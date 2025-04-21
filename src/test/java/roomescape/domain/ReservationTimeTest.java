package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    void 예약시간_id는_null일_수_없다() {
        // when & then
        assertThatThrownBy(() -> new ReservationTime(null, LocalTime.of(2, 20)))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 id는 null일 수 없습니다.");
    }

    @Test
    void 예약시간은_null일_수_없다() {
        // when & then
        assertThatThrownBy(() -> new ReservationTime(1L,null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 시간은 null일 수 없습니다.");
    }
}
