package roomescape.domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    void validateId() {
        Assertions.assertThatThrownBy(() ->   new ReservationTime(null, LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] id값이 존재하지 않습니다.");
    }
}
