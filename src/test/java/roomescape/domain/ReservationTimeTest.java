package roomescape.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @DisplayName("값이 null이면 예외가 발생한다")
    @Test
    void notNull() {
        Assertions.assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("값은 null이 될 수 없습니다.");
    }
}
