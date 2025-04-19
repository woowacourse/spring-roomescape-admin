package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("예약자 이름은 null이면 예외를 발생한다.")
    @Test
    void validateNameNullThrowExceptionTest() {

        // given
        final LocalDate date = LocalDate.now();
        final LocalTime time = LocalTime.now().plusHours(1);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, null, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("예약자 이름이 비어있으면 예외를 발생한다.")
    @Test
    void validateNameBlankThrowExceptionTest() {

        // given
        final String name = "";
        final LocalDate date = LocalDate.now();
        final LocalTime time = LocalTime.now().plusHours(1);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("예약자 이름이 띄어쓰기면 예외를 발생한다.")
    @Test
    void validateNameSpacingThrowExceptionTest() {

        // given
        final String name = " ";
        final LocalDate date = LocalDate.now();
        final LocalTime time = LocalTime.now().plusHours(1);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @DisplayName("예약 날짜가 이전 날짜면 예외를 발생한다.")
    @Test
    void validateDatePreviousThrowExceptionTest() {

        // given
        final String name = "체체";
        final LocalDate date = LocalDate.of(2023, 6, 6);
        final LocalTime time = LocalTime.of(18, 0);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 과거일 수 없습니다.");
    }

    @DisplayName("예약 날짜가 오늘이며 시간이 과거일 경우 예외를 발생한다.")
    @Test
    void validateDateSameAndPreviousTimeThrowExceptionTest() {

        // given
        final String name = "체체";
        final LocalDate date = LocalDate.now();
        final LocalTime time = LocalTime.now().minusHours(1);

        // when & then
        assertThatThrownBy(() -> new Reservation(1L, name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 과거일 수 없습니다.");
    }
}
