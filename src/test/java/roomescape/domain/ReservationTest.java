package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    @DisplayName("예약자 이름은 숫자로만 구성될 수 없다.")
    void validateName() {
        // given
        String invalidName = "123";

        // when & then
        assertThatThrownBy(() -> new Reservation(invalidName, LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜는 null일 수 없다.")
    void validateDate() {
        // given
        LocalDate invalidDate = null;

        // when & then
        assertThatThrownBy(() -> new Reservation("미아", invalidDate, LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간은 null일 수 없다.")
    void validateTime() {
        // given
        LocalTime invalidTime = null;

        // when & then
        assertThatThrownBy(() -> new Reservation("미아", LocalDate.now(), invalidTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미 초기화된 예약 ID를 초기화(수정)할 경우 예외가 발생한다.")
    void initializeId() {
        // given
        Reservation reservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        reservation.initializeId(1L);

        // when & then
        assertThatThrownBy(() -> reservation.initializeId(2L))
                .isInstanceOf(IllegalStateException.class);
    }
}
