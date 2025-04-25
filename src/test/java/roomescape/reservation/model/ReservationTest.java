package roomescape.reservation.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private static final long VALID_ID = 1;
    private static final String VALID_NAME = "포비";
    private static final LocalDate VALID_DATE = LocalDate.now();
    private static final LocalTime VALID_TIME = LocalTime.now();

    @ParameterizedTest
    @DisplayName("이름이 빈 문자열이거나 null이면 예외가 발생한다.")
    @NullAndEmptySource
    void nameEmptyExceptionTest(String invalidName) {
        // given
        ReservationTime validTime = new ReservationTime(1L, VALID_TIME);

        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, invalidName, VALID_DATE, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이름을 입력해주세요.");
    }

    @ParameterizedTest
    @DisplayName("날짜 값이 null이면 예외가 발생한다.")
    @NullSource
    void dateExceptionTest(LocalDate invalidDate) {
        // given
        ReservationTime validTime = new ReservationTime(1L, VALID_TIME);

        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, VALID_NAME, invalidDate, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 날짜를 입력해주세요.");
    }

    @ParameterizedTest
    @DisplayName("시간 값이 null이면 예외가 발생한다.")
    @NullSource
    void timeExceptionTest(ReservationTime invalidTime) {
        // when & then
        assertThatThrownBy(() -> new Reservation(VALID_ID, VALID_NAME, VALID_DATE, invalidTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시간을 입력해주세요.");
    }
}
