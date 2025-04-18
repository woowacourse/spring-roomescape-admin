package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ReservationTest {

    @DisplayName("예약은 null이거나 공백인 이름을 허용하지 않는다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateName(String invalidName) {
        assertThatThrownBy(() -> new Reservation(1L, invalidName, LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 이름입니다.");
    }

    @DisplayName("예약은 비어있는 날짜를 허용하지 않는다.")
    @Test
    void validateDate() {
        LocalDate nullDate = null;
        assertThatThrownBy(() -> new Reservation(1L, "kim", nullDate, LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다.");
    }

    @DisplayName("예약은 비어있는 시간을 허용하지 않는다.")
    @Test
    void validateTime() {
        LocalTime nullTime = null;
        assertThatThrownBy(() -> new Reservation(1L, "kim", LocalDate.now(), nullTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 시간입니다.");
    }
}