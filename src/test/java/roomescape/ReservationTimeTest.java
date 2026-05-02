package roomescape;

import org.junit.jupiter.api.Test;
import roomescape.domain.entity.ReservationTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTimeTest {
    @Test
    void 예약_시간이_null이면_예외를_던진다() {
        assertThatThrownBy(() -> ReservationTime.create(
                1L,
                null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
