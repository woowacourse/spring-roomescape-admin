package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        Long id = 1L;
        LocalTime startAt = LocalTime.now();

        assertThatCode(() -> new ReservationTime(id, startAt))
                .doesNotThrowAnyException();
    }
}
