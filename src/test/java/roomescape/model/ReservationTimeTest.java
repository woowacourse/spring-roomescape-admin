package roomescape.model;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTimeTest {

    @Test
    @DisplayName("시간 값이 null이면 예외가 발생한다")
    void timeExceptionTest() {
        // given
        LocalTime invalidTime = null;
        long validId = 1;

        // when & then
        assertThatThrownBy(() -> new ReservationTime(validId, invalidTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
