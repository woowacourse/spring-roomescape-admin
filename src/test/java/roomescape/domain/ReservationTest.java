package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"123", ""})
    @DisplayName("예약자 이름은 null이거나 숫자로만 구성될 수 없다.")
    void validateName(String invalidName) {
        // when & then
        assertThatThrownBy(() -> new Reservation(invalidName, LocalDate.now(), LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource(value = "invalidDates")
    @DisplayName("예약 날짜는 null이거나 현재 날짜 이전일 수 없다.")
    void validateDate(LocalDate invalidDate) {
        // when & then
        assertThatThrownBy(() -> new Reservation("미아", invalidDate, LocalTime.now()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<LocalDate> invalidDates() {
        return Stream.of(null, LocalDate.now().minusDays(2L));
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
