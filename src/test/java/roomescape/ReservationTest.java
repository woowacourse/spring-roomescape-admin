package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    @DisplayName("이름이 빈 문자열이면 예외가 발생한다")
    void nameExceptionTest() {
        // given
        String invalidName = "";
        long validId = 1;
        LocalDate validDate = LocalDate.now();
        LocalTime validTime = LocalTime.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(validId, invalidName, validDate, validTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜 값이 null이면 예외가 발생한다")
    void dateExceptionTest() {
        // given
        LocalDate invalidDate = null;
        String validName = "포비";
        long validId = 1;
        LocalTime validTime = LocalTime.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(validId, validName, invalidDate, validTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시간 값이 null이면 예외가 발생한다")
    void timeExceptionTest() {
        // given
        LocalTime invalidTime = null;
        String validName = "포비";
        long validId = 1;
        LocalDate validDate = LocalDate.now();

        // when & then
        assertThatThrownBy(() -> new Reservation(validId, validName, validDate, invalidTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
