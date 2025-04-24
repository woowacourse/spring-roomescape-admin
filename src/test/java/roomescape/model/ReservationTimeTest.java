package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @DisplayName("시작 시간이 없으면 예약 시간을 생성할 수 없다.")
    @Test
    void createReservationTimeWithoutStartAt() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
