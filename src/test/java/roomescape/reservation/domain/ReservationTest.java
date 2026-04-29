package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("예약 일자를 조회한다.")
    @Test
    void getReservedDate() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(
                LocalDate.of(2025, 4, 28),
                LocalTime.of(4, 23)
        );

        Reservation reservation = new Reservation(
                "name",
                localDateTime
        );

        //when & then
        assertThat(reservation.getReservedDate())
                .isEqualTo(LocalDate.of(2025, 4, 28));
    }

    @DisplayName("예약 시간을 조회한다.")
    @Test
    void getReservedTime() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(
                LocalDate.of(2025, 4, 28),
                LocalTime.of(4, 23)
        );

        Reservation reservation = new Reservation(
                "name",
                localDateTime
        );

        //when & then
        assertThat(reservation.getReservedTime())
                .isEqualTo(LocalTime.of(4, 23));
    }
}
