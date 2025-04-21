package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReservationTest {
    
    private static final int MAX_NAME_LENGTH = 255;

    @Test
    void 이름이_BLACK인_경우_예외가_발생한다() {
        assertAll(
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Reservation(null, " ", LocalDateTime.MAX))
                        .withMessage("이름은 공백이거나 NULL일 수 없습니다."),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Reservation(null, null, LocalDateTime.MAX))
                        .withMessage("이름은 공백이거나 NULL일 수 없습니다."),
                () -> assertThatIllegalArgumentException()
                        .isThrownBy(() -> new Reservation(null, "", LocalDateTime.MAX))
                        .withMessage("이름은 공백이거나 NULL일 수 없습니다.")
        );
    }

    @Test
    void 이름의_길이가_255_이하인_경우_예외가_발생하지_않는다() {
        // given
        String name = "a".repeat(MAX_NAME_LENGTH);

        // when & then
        assertDoesNotThrow(() -> new Reservation(null, name, LocalDateTime.MAX));
    }

    @Test
    void 이름의_길이가_255_초과인_경우_예외가_발생한다() {
        // given
        String name = "a".repeat(MAX_NAME_LENGTH + 1);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Reservation(null, name, LocalDateTime.MAX))
                .withMessage("이름의 길이는 255 초과할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "yyyy-MM-dd, HH:mm, 2024-12-23, 15:00",
            "yyyy/MM/dd, HH:mm, 2024/12/23, 15:00",
            "dd-MM-yyyy, HH:mm, 23-12-2024, 15:00",
            "MM/dd/yyyy, HH:mm, 12/23/2024, 15:00"
    })
    void 시간과_날짜를_포멧팅_형식으로_반환한다(String datePattern, String timePattern, String dateExpected, String timeExpected) {
        // given
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timePattern);

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 23, 15, 0);
        Reservation reservation = new Reservation(null, "testName", dateTime);

        // when
        String resultDate = reservation.formatDateTime(dateFormatter);
        String resultTime = reservation.formatDateTime(timeFormatter);

        // then
        assertAll(
                () -> assertThat(resultDate).isEqualTo(dateExpected),
                () -> assertThat(resultTime).isEqualTo(timeExpected)
        );
    }
}
