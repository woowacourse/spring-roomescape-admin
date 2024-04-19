package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {

    @Test
    @DisplayName("동등성을 비교한다.")
    void equalsTest() {
        ReservationTime reservationTime = new ReservationTime("15:30");
        assertThat(reservationTime).isEqualTo(new ReservationTime("15:30"));
    }
}
