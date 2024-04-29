package roomescape.dto;

import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTimeCreateRequestTest {
    @Test
    void convert_Request_To_ReservationTime() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.parse("15:30"));
        ReservationTimeCreateRequest request = new ReservationTimeCreateRequest(LocalTime.parse("15:30"));
        ReservationTime target = request.toTime(1L);

        assertThat(reservationTime).usingRecursiveComparison().isEqualTo(target);
    }
}
