package roomescape.reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class ReservationTest {

    @Test
    @DisplayName("Reservation 생성 테스트")
    void generateReservation() {
        //given
        Long reservationId = 1L;
        String reservationName = "hippo";
        LocalDate nowDate = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 31));
        //when & then
        assertThatCode(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("예약자명 빈 값 테스트")
    void throwsExceptionIfReservationNameIsBlank(String reservationName) {
        //given
        Long reservationId = 1L;
        LocalDate nowDate = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 31));

        //when & then
        assertThatThrownBy(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약자명 null 테스트")
    void throwsExceptionIfReservationNameIsNull() {
        //given
        Long reservationId = 1L;
        String reservationName = null;
        LocalDate nowDate = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 31));

        //when & then
        assertThatThrownBy(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜 null 테스트")
    void throwsExceptionIfReservationDateIsNull() {
        //given
        Long reservationId = 1L;
        String reservationName = "hippo";
        LocalDate nowDate = null;
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 31));

        //when & then
        assertThatThrownBy(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간 null 테스트")
    void throwsExceptionIfReservationTimeIsNull() {
        //given
        Long reservationId = 1L;
        String reservationName = "hippo";
        LocalDate nowDate = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = null;

        //when & then
        assertThatThrownBy(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜가 현재 날짜보다 이전의 날짜인 경우 예외 발생 테스트")
    void throwsExceptionIfReservationDateIsInThePast() {
        //given
        Long reservationId = 1L;
        String reservationName = "hippo";
        LocalDate nowDate = LocalDate.of(2024, 10, 21);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 31));

        //when & then
        assertThatThrownBy(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜와 시간이 현재 날짜와 시간보다 이전의 날짜인 경우 예외 발생 테스트")
    void throwsExceptionIfReservationDateTimeIsInThePast() {
        //given
        Long reservationId = 1L;
        String reservationName = "hippo";
        LocalDate nowDate = LocalDate.now();
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now().minusMinutes(10));

        //when & then
        assertThatThrownBy(() -> new Reservation(reservationId, reservationName, nowDate, reservationTime)).isInstanceOf(IllegalArgumentException.class);
    }
}

