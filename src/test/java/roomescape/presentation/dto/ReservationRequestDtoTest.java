package roomescape.presentation.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ReservationRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("예약자명을 입력하지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_WhenNameIsMissing(String name) {
        // given
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(name, LocalDate.now(), 1L);

        // when
        var validate = validator.validate(reservationRequestDto);

        // then
        assertAll(
                () -> assertThat(validate)
                        .hasSize(1),
                () -> assertThat(validate.iterator().next().getMessage())
                        .isEqualTo("예약자명은 필수입니다."),
                () -> assertThat(validate.iterator().next().getPropertyPath().toString())
                        .isEqualTo("name")
        );
    }

    @DisplayName("예약 날짜를 입력하지 않은 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenDateIsMissing() {
        // given
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("브라운", null, 1L);

        // when
        var validate = validator.validate(reservationRequestDto);

        // then
        assertAll(
                () -> assertThat(validate)
                        .hasSize(1),
                () -> assertThat(validate.iterator().next().getMessage())
                        .isEqualTo("예약 날짜는 필수입니다."),
                () -> assertThat(validate.iterator().next().getPropertyPath().toString())
                        .isEqualTo("date")
        );
    }

    @DisplayName("예약 시간 ID를 입력하지 않은 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenTimeIdIsMissing() {
        // given
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("브라운", LocalDate.now(), null);

        // when
        var validate = validator.validate(reservationRequestDto);

        // then
        assertAll(
                () -> assertThat(validate)
                        .hasSize(1),
                () -> assertThat(validate.iterator().next().getMessage())
                        .isEqualTo("예약 시간 ID는 필수입니다."),
                () -> assertThat(validate.iterator().next().getPropertyPath().toString())
                        .isEqualTo("timeId")
        );
    }
}
