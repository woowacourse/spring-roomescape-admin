package roomescape.reservation.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private static final long VALID_ID = 1;
    private static final String VALID_NAME = "포비";

    @Test
    @DisplayName("이름이 빈 문자열이면 예외가 발생한다")
    void nameEmptyExceptionTest() {
        // given
        String invalidName = "";
        LocalDate validDate = LocalDate.now();
        LocalTime validTime = LocalTime.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, invalidName, validDate, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이름을 입력해주세요.");
    }

    @Test
    @DisplayName("이름이 null이면 예외가 발생한다")
    void nameNullExceptionTest() {
        // given
        String invalidName = null;
        LocalDate validDate = LocalDate.now();
        LocalTime validTime = LocalTime.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, invalidName, validDate, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이름을 입력해주세요.");
    }

    @Test
    @DisplayName("날짜 값이 null이면 예외가 발생한다")
    void dateExceptionTest() {
        // given
        LocalDate invalidDate = null;
        LocalTime validTime = LocalTime.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, VALID_NAME, invalidDate, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 날짜를 입력해주세요.");
    }

    @Test
    @DisplayName("시간 값이 null이면 예외가 발생한다")
    void timeExceptionTest() {
        // given
        LocalTime invalidTime = null;
        LocalDate validDate = LocalDate.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, VALID_NAME, validDate, invalidTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시간을 입력해주세요.");
    }
}
