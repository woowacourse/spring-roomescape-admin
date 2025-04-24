package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private static final int MAX_NAME_LENGTH = 255;
    private static final LocalDate TEST_DATE = LocalDate.MAX;
    private static final ReservationTime TEST_RESERVATION_TIME = new ReservationTime(null, LocalTime.MAX);

    @Test
    void 이름이_BLACK인_경우_예외가_발생한다() {
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Reservation(null, " ", TEST_DATE, TEST_RESERVATION_TIME))
                        .withMessage("이름은 공백이거나 NULL일 수 없습니다."),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Reservation(null, null, TEST_DATE, TEST_RESERVATION_TIME))
                        .withMessage("이름은 공백이거나 NULL일 수 없습니다."),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Reservation(null, "", TEST_DATE, TEST_RESERVATION_TIME))
                        .withMessage("이름은 공백이거나 NULL일 수 없습니다.")
        );
    }

    @Test
    void 이름의_길이가_255_이하인_경우_예외가_발생하지_않는다() {
        // given
        String name = "a".repeat(MAX_NAME_LENGTH);

        // when & then
        assertDoesNotThrow(() -> new Reservation(null, name, TEST_DATE, TEST_RESERVATION_TIME));
    }

    @Test
    void 이름의_길이가_255_초과인_경우_예외가_발생한다() {
        // given
        String name = "a".repeat(MAX_NAME_LENGTH + 1);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Reservation(null, name, TEST_DATE, TEST_RESERVATION_TIME))
                .withMessage("이름의 길이는 255 초과할 수 없습니다.");
    }
}
