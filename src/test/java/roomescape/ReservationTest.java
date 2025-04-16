package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTest {

    @DisplayName("예약을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void createTest(int differentSecond) {
        // given
        int id = 1;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusSeconds(differentSecond);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(id, name, reservationDate, reservationTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 일시로 예약을 생성할 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void shouldThrowException_WhenCreatePastReservation(int differentSecond) {
        // given
        int id = 1;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().minusSeconds(differentSecond);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(id, name, reservationDate, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거 일시로 예약을 생성할 수 없습니다.");
    }

    @DisplayName("예약자명이 null 또는 빈 문자열인 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_WhenCreateWithEmptyName(String emptyName) {
        // given
        int id = 1;
        LocalDateTime reservationDateTime = LocalDateTime.now().plusHours(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(id, emptyName, reservationDate, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자명이 입력되지 않았습니다.");
    }

    @DisplayName("예약 날짜가 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCreateWithNullDate() {
        // given
        int id = 1;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusHours(1);
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(id, name, null, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜가 입력되지 않았습니다.");
    }

    @DisplayName("예약 시간이 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCreateWithNullTime() {
        // given
        int id = 1;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusHours(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();

        // when & then
        assertThatCode(() -> new Reservation(id, name, reservationDate, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간이 입력되지 않았습니다.");
    }
}
