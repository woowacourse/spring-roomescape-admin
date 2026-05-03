package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    void 예약_시간이_null이면_예외_테스트() {
        assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
