package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @DisplayName("비어있는 ID값으로 예약시간을 생성할 수 없다")
    @Test
    void cannotCreateBecauseNullId() {
        Long nullId = null;
        assertThatThrownBy(() -> new ReservationTime(nullId, LocalTime.now()));
    }

    @DisplayName("비어있는 예약 시간시간으로 예약시간을 생성할 수 없다")
    @Test
    void cannotCreateBecauseNullStartAt() {
        LocalTime nullStartAt = null;
        assertThatThrownBy(() -> new ReservationTime(1L, nullStartAt));
    }
}