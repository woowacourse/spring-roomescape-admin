package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ScheduleTest {
    @DisplayName("일정은 현재 이전으로 설정하면 예외를 던진다.")
    @Test
    void invalidTermSchedule() {
        //given
        String name = "lini";
        String dateBeforeCurrent = "2023-10-04";
        String time = "10:00";
        ReservationTime reservationTime = new ReservationTime(1, time);

        //when&then
        assertThatThrownBy(() -> new Reservation(name, dateBeforeCurrent, reservationTime))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("현재보다 이전으로 일정을 설정할 수 없습니다.");
    }

    @DisplayName("일정은 올바르지 않은 날짜로 설정하면 예외를 던진다.")
    @Test
    void invalidDateSchedule() {
        //given
        String name = "lini";
        String invalidDate = "";
        String time = "10:00";
        ReservationTime reservationTime = new ReservationTime(1, time);

        //when&then
        assertThatThrownBy(() -> new Reservation(name, invalidDate, reservationTime))
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("올바르지 않은 날짜입니다. date: '" + invalidDate + "'");
    }

    @DisplayName("시간은 `HH:mm` 형식으로 반환한다.")
    @Test
    void getTime() {
        //given
        String name = "lini";
        String date = LocalDate.now().plusDays(1).toString();
        LocalTime time = LocalTime.now();
        ReservationTime reservationTime = new ReservationTime(1, time);

        //when
        Reservation reservation = new Reservation(name, date, reservationTime);

        //when&then
        assertThat(reservation.getTime()).isEqualTo(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
