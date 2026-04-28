package roomescape.reservationTime.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservationTime.ReservationTime;

class ReservationTimeRequestTest {

    @Test
    void toDomain_변환() {
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(10, 0));

        ReservationTime time = request.toDomain(1L);

        assertThat(time.getId()).isEqualTo(1L);
        assertThat(time.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void toDomain_id가_주입된다() {
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(10, 0));

        ReservationTime first = request.toDomain(1L);
        ReservationTime second = request.toDomain(2L);

        assertThat(first.getId()).isEqualTo(1L);
        assertThat(second.getId()).isEqualTo(2L);
    }
}
