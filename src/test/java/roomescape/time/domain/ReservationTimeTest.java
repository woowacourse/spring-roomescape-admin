package roomescape.time.domain;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReservationTimeTest {

    @Test
    void 시간으로_예약시간이_정상적으로_생성된다() {
        final LocalTime time = LocalTime.of(12, 30);

        assertThatNoException()
                .isThrownBy(() -> new ReservationTime(time));
    }

    @Test
    void ID와_시간으로_예약시간이_정상적으로_생성된다() {
        final long id = 1L;
        final LocalTime time = LocalTime.of(12, 30);

        assertThatNoException()
                .isThrownBy(() -> new ReservationTime(id, time));
    }

    @Test
    void 시간이_비어있다면_예외가_발생한다() {
        assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
