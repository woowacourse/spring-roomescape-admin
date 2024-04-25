package roomescape.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @DisplayName("값이 null이면 예외가 발생한다")
    @Test
    void notNull() {
        Assertions.assertThatThrownBy(() -> ReservationTime.from(null))
                .isInstanceOf(NullPointerException.class);

    }
}
