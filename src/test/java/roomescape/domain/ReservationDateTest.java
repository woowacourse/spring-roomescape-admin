package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ReservationDateTest {
    @Test
    @DisplayName("문자열을 통해 예약 날짜를 생성한다.")
    void create_domain_with_string() {
        assertThatCode(() -> ReservationDate.from("2023-10-20"))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023.10.20", "9999-99-99"})
    @DisplayName("적절한 형식이 아니면 예외를 발생한다")
    void throw_exception_when_format_is_not_valid(final String date) {
        assertThatThrownBy(() -> ReservationDate.from(date))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
