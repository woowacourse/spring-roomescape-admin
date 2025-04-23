package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class ReservationCreateRequestTest {

    @DisplayName("예약자 이름은 null이면 예외를 발생한다.")
    @Test
    void validateNameNullThrowExceptionTest() {

        // given
        final LocalDate date = LocalDate.of(2025, 4, 21);
        final ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        // when & then
        assertThatThrownBy(() -> new ReservationCreateRequest(null, date, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값으로 예약할 수 없습니다.");
    }

    @DisplayName("예약자 이름이 비어있으면 예외를 발생한다.")
    @Test
    void validateNameBlankThrowExceptionTest() {

        // given
        final String name = "";
        final LocalDate date = LocalDate.of(2025, 4, 21);
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new ReservationCreateRequest(name, date, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값으로 예약할 수 없습니다.");
    }

    @DisplayName("예약자 이름이 띄어쓰기면 예외를 발생한다.")
    @Test
    void validateNameSpacingThrowExceptionTest() {

        // given
        final String name = " ";
        final LocalDate date = LocalDate.of(2025, 4, 21);
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new ReservationCreateRequest(name, date, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값으로 예약할 수 없습니다.");
    }

    @DisplayName("날짜가 비어있으면 예외가 발생한다.")
    @Test
    void validateDateNullThrowExceptionTest() {

        // given
        final String name = "체체";
        final LocalTime time = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> new ReservationCreateRequest(name, null, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값으로 예약할 수 없습니다.");
    }

    @DisplayName("시간이 비어있으면 예외가 발생한다.")
    @Test
    void validateTimeNullThrowExceptionTest() {

        // given
        final String name = "체체";
        final LocalDate date = LocalDate.of(2025, 4, 21);

        // when & then
        assertThatThrownBy(() -> new ReservationCreateRequest(name, date, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 값으로 예약할 수 없습니다.");
    }
}
