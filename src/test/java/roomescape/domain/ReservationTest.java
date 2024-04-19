package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {
    @DisplayName("정상 생성 테스트")
    @Test
    void validCreate() {
        assertDoesNotThrow(() -> new Reservation(1, "aa", "2023-10-10", "10:00"));
    }

    @DisplayName("비정상 날짜 생성 불가 테스트")
    @Test
    void invalidDateInvalidCreate() {
        assertThatThrownBy(() -> new Reservation(1, "aa", "20222-10-10", "10:10"))
                .isInstanceOf(DateTimeParseException.class);
    }

    @DisplayName("비정상 시간 생성 불가 테스트")
    @Test
    void invalidTimeInvalidCreate() {
        assertThatThrownBy(() -> new Reservation(1, "aa", "2023-10-10", "25:00"))
                .isInstanceOf(DateTimeParseException.class);
    }
}
