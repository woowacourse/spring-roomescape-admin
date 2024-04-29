package roomescape.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

class ReservationTimeResponseTest {

    @Test
    @DisplayName("ReservationTime 객체로 ReservationTimeResponse를 만든다.")
    void createReservationTimeResponse() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));
        ReservationTimeResponse expected = new ReservationTimeResponse(1L, "10:00");

        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(time);

        assertThat(reservationTimeResponse).isEqualTo(expected);
    }

    @Test
    @DisplayName("ReservationTimeResponse 객체로 ReservationTime을 만든다.")
    void createReservationTime() {
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(1L, "10:00");
        ReservationTime expected = new ReservationTime(1L, LocalTime.of(10, 0));

        ReservationTime reservationTime = reservationTimeResponse.toReservationTime();

        assertThat(reservationTime).isEqualTo(expected);
    }
}
