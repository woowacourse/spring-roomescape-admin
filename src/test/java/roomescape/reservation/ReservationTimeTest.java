package roomescape.reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class ReservationTimeTest {

    @Test
    @DisplayName("ReservationTime 생성 테스트")
    void generateReservationTime() {
        //given
        Long timeId = 1L;
        LocalTime time = LocalTime.of(10, 31);
        //when & then
        assertThatCode(() -> new ReservationTime(timeId, time)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("예약 시간 null 테스트")
    void throwExceptionIfReservationTime() {
        //given
        Long timeId = 1L;
        LocalTime time = null;
        //when & then
        assertThatThrownBy(() -> new ReservationTime(timeId, time)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간이 현재 시간 이전인 경우 True")
    void returnTrueIfReservationTimeIsBeforeNow() {
        //given
        Long timeId = 1L;
        ReservationTime reservationTime = new ReservationTime(timeId, LocalTime.now().minusMinutes(10));
        // then
        boolean isPast = reservationTime.isBefore(LocalTime.now());
        //when
        assertThat(isPast).isTrue();
    }

    @Test
    @DisplayName("예약 시간이 현재 시간 이후인 경우 False")
    void returnFalseIfReservationTimeIsBeforeNow() {
        //given
        Long timeId = 1L;
        ReservationTime reservationTime = new ReservationTime(timeId, LocalTime.now().plusMinutes(10));
        // then
        boolean isPast = reservationTime.isBefore(LocalTime.now());
        //when
        assertThat(isPast).isFalse();
    }
}
