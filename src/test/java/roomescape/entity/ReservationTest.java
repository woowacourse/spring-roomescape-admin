package roomescape.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    @DisplayName("예약 엔티티에 아이디와 예약 시간을 부여한다.")
    void assignId() {
        // given
        Reservation reservation = new Reservation(
                null,
                "seyang",
                LocalDate.of(2024, 4, 24),
                null);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        Reservation expected = new Reservation(
                2L,
                "seyang",
                LocalDate.of(2024, 4, 24),
                new ReservationTime(1L, LocalTime.of(10, 0)));

        // when
        Reservation actual = reservation.assign(2L, reservationTime);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
