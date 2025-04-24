package roomescape.presentation.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @DisplayName("예약 가능 시간을 입력하지 않은 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenTimeIsMissing() {
        // given
        ReservationTimeRequestDto reservationTimeRequestDto = new ReservationTimeRequestDto(null);

        // when
        var validate = validator.validate(reservationTimeRequestDto);

        // then
        assertAll(
                () -> assertThat(validate)
                        .hasSize(1),
                () -> assertThat(validate.iterator().next().getMessage())
                        .isEqualTo("가능한 예약 시간은 필수입니다."),
                () -> assertThat(validate.iterator().next().getPropertyPath().toString())
                        .isEqualTo("startAt")
        );
    }
}
