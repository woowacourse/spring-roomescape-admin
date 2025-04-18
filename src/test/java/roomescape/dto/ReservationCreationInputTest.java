package roomescape.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import roomescape.exception.ValidationExceptionMessage;

class ReservationCreationInputTest {

    @DisplayName("null이거나 공백인 이름을 허용하지 않는다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateName(String invalidName) {
        assertThatThrownBy(() -> new ReservationCreationInput(invalidName, LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ValidationExceptionMessage.NULL_OR_BLANK_NAME.getContent());
    }

    @DisplayName("비어있는 날짜를 허용하지 않는다.")
    @Test
    void validateDate() {
        LocalDate nullDate = null;
        assertThatThrownBy(() -> new ReservationCreationInput("kim", nullDate, LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ValidationExceptionMessage.NULL_DATE.getContent());
    }

    @DisplayName("비어있는 시간을 허용하지 않는다.")
    @Test
    void validateTime() {
        LocalTime nullTime = null;
        assertThatThrownBy(() -> new ReservationCreationInput("kim", LocalDate.now(), nullTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ValidationExceptionMessage.NULL_TIME.getContent());
    }
}