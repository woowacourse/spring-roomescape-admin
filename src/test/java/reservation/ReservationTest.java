package reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationTest {
    @DisplayName("예약 정보와 id가 같은지 확인 : True")
    @Test
    void isSameIdTrueTest() {
        //given
        Reservation reservation = new Reservation(1L,"test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        //when

        //then
        Assertions.assertThat(reservation.isSameId(1L)).isTrue();
    }

    @DisplayName("예약 정보와 id가 같은지 확인 : False")
    @Test
    void isSameIdFalseTest() {
        //given
        Reservation reservation = new Reservation(1L,"test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        //when

        //then
        Assertions.assertThat(reservation.isSameId(2L)).isFalse();
    }
}
