package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.entity.ReservationTime.RESERVATION_DURATION_HOUR;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    @DisplayName("예약이 끝나는 시간을 계산할 수 있다")
    @Test
    void calculateReservationEndTimeTest() {
        LocalDateTime startTime = LocalDateTime.of(2024, 4, 20, 12, 30);
        Reservation reservation = new Reservation("리비", startTime.toLocalDate(), startTime.toLocalTime());

        assertThat(reservation.getEndDateTime()).isEqualTo(startTime.plusHours(RESERVATION_DURATION_HOUR));
    }
}
