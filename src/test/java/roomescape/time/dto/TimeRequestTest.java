package roomescape.time.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.time.ReservationTime;

class TimeRequestTest {

    @Test
    void toDomain_변환() {
        TimeRequest request = new TimeRequest(LocalTime.of(10, 0));

        ReservationTime time = request.toDomain(1L);

        assertThat(time.getId()).isEqualTo(1L);
        assertThat(time.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void toDomain_id가_주입된다() {
        TimeRequest request = new TimeRequest(LocalTime.of(10, 0));

        ReservationTime first = request.toDomain(1L);
        ReservationTime second = request.toDomain(2L);

        assertThat(first.getId()).isEqualTo(1L);
        assertThat(second.getId()).isEqualTo(2L);
    }
}