package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private final Clock clock = Clock.fixed(Instant.parse("2025-04-20T10:00:00Z"), ZoneId.systemDefault());

    @DisplayName("예약자 이름은 null이면 예외를 발생한다.")
    @Test
    void validateNameNullThrowExceptionTest() {

        // given
        final LocalDate date = LocalDate.of(2025, 4, 21);
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, null, date, time, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("예약자 이름이 비어있으면 예외를 발생한다.")
    @Test
    void validateNameBlankThrowExceptionTest() {

        // given
        final String name = "";
        final LocalDate date = LocalDate.of(2025, 4, 21);
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("예약자 이름이 띄어쓰기면 예외를 발생한다.")
    @Test
    void validateNameSpacingThrowExceptionTest() {

        // given
        final String name = " ";
        final LocalDate date = LocalDate.of(2025, 4, 21);
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("예약 날짜가 이전 날짜면 예외를 발생한다.")
    @Test
    void validateDatePreviousThrowExceptionTest() {

        // given
        final String name = "체체";
        final LocalDate date = LocalDate.of(2025, 4, 19);
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 과거일 수 없습니다.");
    }

    @DisplayName("예약 날짜가 오늘이며 시간이 과거일 경우 예외를 발생한다.")
    @Test
    void validateDateSameAndPreviousTimeThrowExceptionTest() {

        // given
        final String name = "체체";
        final LocalDate date = LocalDate.of(2025, 4, 20);
        final LocalTime time = LocalTime.of(9, 59);
        
        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 과거일 수 없습니다.");
    }
}
