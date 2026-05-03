package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class ReservationTimeTest {

    @Test
    void 시간생성시_시간정보가_null이면_예외가_발생한다() {
        LocalTime nullTime = null;

        Assertions.assertThatThrownBy(() -> new ReservationTime(nullTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 예약시간은 Null일 수 없습니다.");
    }

}
