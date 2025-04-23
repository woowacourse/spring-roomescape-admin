package roomescape.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class ReservationTimeTest {

    @DisplayName("예약 시간이 null 일 수 없다.")
    @Test
    void reservationTimeTest() {
        LocalTime nullLocalTime = null;

        Assertions.assertThatThrownBy(() -> new ReservationTime(1L, nullLocalTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
