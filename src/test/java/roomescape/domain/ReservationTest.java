package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.TestFixture.*;

class ReservationTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"123", "", " "})
    @DisplayName("예약자 이름은 빈칸이거나 숫자로만 구성될 수 없다.")
    void validateName(String invalidName) {
        // when & then
        assertThatThrownBy(() -> new Reservation(invalidName, MIA_RESERVATION_DATE, MIA_RESERVATION_TIME))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource(value = "invalidDates")
    @DisplayName("예약 날짜는 null이 아니며, 현재 날짜 이후이다.")
    void validateDate(LocalDate invalidDate) {
        // when & then
        assertThatThrownBy(() -> new Reservation(USER_MIA, invalidDate, MIA_RESERVATION_TIME))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<LocalDate> invalidDates() {
        return Stream.of(null, LocalDate.now().minusDays(2L));
    }

    @ParameterizedTest
    @MethodSource(value = "invalidTimes")
    @DisplayName("예약 시간은 null이 아니며, 10분 단위이다.")
    void validateTime(LocalTime invalidTime) {
        // when & then
        assertThatThrownBy(() -> new Reservation(USER_MIA, MIA_RESERVATION_DATE, invalidTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<LocalTime> invalidTimes() {
        return Stream.of(null, LocalTime.of(1, 23));
    }

    @ParameterizedTest
    @MethodSource("reservationsAndExpectedResult")
    @DisplayName("예약이 동일한 예약 시간을 갖는지 확인한다.")
    void hasSameDateTime(LocalDate date, LocalTime time, boolean expectedResult) {
        // given
        Reservation reservation = MIA_RESERVATION();

        // when
        boolean actualResult = reservation.hasSameDateTime(date, time);

        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> reservationsAndExpectedResult() {
        return Stream.of(
                Arguments.of(MIA_RESERVATION_DATE, MIA_RESERVATION_TIME, true),
                Arguments.of(TOMMY_RESERVATION_DATE, TOMMY_RESERVATION_TIME, false)
        );
    }
}
