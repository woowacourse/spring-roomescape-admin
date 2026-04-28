package roomescape.time.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.time.ReservationTime;

class TimeResponseTest {

    @Test
    void from_변환() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        TimeResponse response = TimeResponse.from(time);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.startAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void from_도메인_필드가_모두_응답에_포함된다() {
        ReservationTime time = new ReservationTime(42L, LocalTime.of(15, 40));

        TimeResponse response = TimeResponse.from(time);

        assertThat(response.id()).isEqualTo(time.getId());
        assertThat(response.startAt()).isEqualTo(time.getStartAt());
    }
}