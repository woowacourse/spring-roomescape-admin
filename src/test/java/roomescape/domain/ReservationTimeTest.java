package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @DisplayName("예약 가능한 시간을 생성할 수 있다.")
    @Test
    void createTest() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);

        // when & then
        assertThatCode(() -> new ReservationTime(startAt))
                .doesNotThrowAnyException();
    }
}
