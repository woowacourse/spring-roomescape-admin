package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationDateTest {

    @Test
    @DisplayName("날짜가 null이면 예외를 발생한다.")
    void throwException_When_DateIsNull() {
        assertThatThrownBy(() -> new ReservationDate(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("LocalDate로 날짜를 생성한다.")
    void makeDate_When_LocalDate() {
        assertThatCode(() -> new ReservationDate(LocalDate.of(2024, 1, 1)))
                .doesNotThrowAnyException();
    }
}
