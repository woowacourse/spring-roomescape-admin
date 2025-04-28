package roomescape.business.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class ReservationTimeTest {

    @Nested
    class 생성_테스트 {

        @Test
        void 시간으로_생성할_수_있다() {
            final LocalTime time = LocalTime.of(10, 0);

            assertThatCode(() -> new ReservationTime(time))
                    .doesNotThrowAnyException();
        }

        @Test
        void 시간이_없으면_예외가_발생한다() {
            assertThatThrownBy(() -> new ReservationTime(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void _9시_이전이면_예외가_발생한다() {
            assertThatThrownBy(() -> new ReservationTime(LocalTime.of(8, 59)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void _11시_이후이면_예외가_발생한다() {
            assertThatThrownBy(() -> new ReservationTime(LocalTime.of(23, 1)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
