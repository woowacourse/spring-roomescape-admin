package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @DisplayName("예약 내역의 속성에 널 값을 입력한 경우 예외 처리한다")
    @Test
    void throwException_NullValue() {
        LocalDate date = LocalDate.parse("2025-04-17");
        LocalTime time = LocalTime.parse("19:00");

        assertThatThrownBy(() -> new Reservation(null, date, new ReservationTime(1L, time)))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("예약 내역의 날짜와 시간 아이디 일치 여부를 확인하는 기능을 구현한다")
    @Test
    void checkSameDateAndTimeId() {
        LocalDate date = LocalDate.parse("2025-04-19");
        LocalTime time = LocalTime.parse("20:00");

        Reservation reservation1 = new Reservation("kim", date, new ReservationTime(1L, time));
        Reservation reservation2 = new Reservation("park", date, new ReservationTime(1L, time));

        assertThat(reservation1.isSameDateAndTimeId(reservation2)).isTrue();
    }
}
