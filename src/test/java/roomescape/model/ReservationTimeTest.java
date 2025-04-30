package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTimeTest {

    @DisplayName("예약 시간 객체를 생성할 수 있다")
    @Test
    void createReservationTime() {
        assertThatCode(() -> new ReservationTime(1L, LocalTime.parse("10:00"))).doesNotThrowAnyException();
    }

    @DisplayName("시간 내역의 아이디 일치 여부를 확인하는 기능을 구현한다")
    @Test
    void checkSameTimeId() {
        LocalTime time = LocalTime.parse("20:00");

        ReservationTime reservationTime1 = new ReservationTime(1L, time);
        ReservationTime reservationTime2 = new ReservationTime(1L, time);

        assertThat(reservationTime1.isSameId(reservationTime2)).isTrue();
    }
}
