package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.ReservationTimeException;

import static org.assertj.core.api.Assertions.assertThatCode;

class ReservationTimeTest {

    @Test
    void 시작시간이_존재하지_않는_예약시간은_생성할_수_없다() {
        assertThatCode(() -> new ReservationTime(null))
                .isInstanceOf(ReservationTimeException.class)
                .hasMessage("시작 시간은 비어있을 수 없습니다");
    }
}
