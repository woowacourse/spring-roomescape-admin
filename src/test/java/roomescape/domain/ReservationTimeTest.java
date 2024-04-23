package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @DisplayName("정상 생성 테스트")
    @Test
    void validCreate() {
        assertDoesNotThrow(() -> new ReservationTime(1, "10:00"));
    }

    @DisplayName("비정상 시간 생성 불가 테스트")
    @Test
    void invalidTimeCreate() {
        assertThatThrownBy(() -> new ReservationTime(1, "25:00"))
                .isInstanceOf(DateTimeParseException.class);
    }
}
