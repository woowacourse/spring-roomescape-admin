package roomescape.business.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class ReservationTimeTest {

    @Nested
    class 생성_테스트 {

        private final long id = 1L;
        private final LocalTime time = LocalTime.of(10, 0);

        @Test
        void 아이디_시간으로_생성할_수_있다() {
            assertThatCode(() -> new ReservationTime(id, time))
                    .doesNotThrowAnyException();
        }

        @Test
        void 시간이_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> new ReservationTime(id, null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
