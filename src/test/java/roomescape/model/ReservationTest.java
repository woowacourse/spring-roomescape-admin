package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.exception.UserIllegalArgumentException;

class ReservationTest {

    @DisplayName("예약을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void createTest(int differentSecond) {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime requestDateTime = LocalDateTime.now().plusSeconds(differentSecond);
        LocalDate requestDate = requestDateTime.toLocalDate();
        LocalTime requestTime = requestDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> Reservation.toEntity(id, name, requestDate, ReservationTime.toEntity(id, requestTime)))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 일시로 예약을 생성할 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void shouldThrowException_WhenCreatePastReservation(int differentSecond) {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime requestDateTime = LocalDateTime.now().minusSeconds(differentSecond);
        LocalDate requestDate = requestDateTime.toLocalDate();
        LocalTime requestTime = requestDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> Reservation.toEntity(id, name, requestDate, ReservationTime.toEntity(id, requestTime)))
                .isInstanceOf(UserIllegalArgumentException.class)
                .hasMessage("과거 일시로 예약을 생성할 수 없습니다.");
    }

    @DisplayName("예약자명이 null 또는 빈 문자열인 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_WhenCreateWithEmptyName(String emptyName) {
        // given
        Long id = 1L;
        LocalDateTime requestDateTime = LocalDateTime.now().plusHours(1);
        LocalDate requestDate = requestDateTime.toLocalDate();
        LocalTime requestTime = requestDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> Reservation.toEntity(id, emptyName, requestDate, ReservationTime.toEntity(id, requestTime)))
                .isInstanceOf(UserIllegalArgumentException.class)
                .hasMessage("예약자명이 입력되지 않았습니다.");
    }

    @DisplayName("예약 날짜가 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCreateWithNullDate() {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime requestDateTime = LocalDateTime.now().plusHours(1);
        LocalTime requestTime = requestDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> Reservation.toEntity(id, name, null, ReservationTime.toEntity(id, requestTime)))
                .isInstanceOf(UserIllegalArgumentException.class)
                .hasMessage("예약 날짜가 입력되지 않았습니다.");
    }

    @DisplayName("예약 시간이 null인 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCreateWithNullTime() {
        // given
        Long id = 1L;
        String name = "브라운";
        LocalDateTime requestDateTime = LocalDateTime.now().plusHours(1);
        LocalDate requestDate = requestDateTime.toLocalDate();

        // when & then
        assertThatCode(() -> Reservation.toEntity(id, name, requestDate, null))
                .isInstanceOf(UserIllegalArgumentException.class)
                .hasMessage("예약 시간이 입력되지 않았습니다.");
    }
}
