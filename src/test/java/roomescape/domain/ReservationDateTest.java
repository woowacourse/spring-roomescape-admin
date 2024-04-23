package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationDateTest {

    @Test
    @DisplayName("동등성을 비교한다.")
    void equalsTest() {
        ReservationDate reservationDate = new ReservationDate("2024-04-20");
        assertThat(reservationDate).isEqualTo(new ReservationDate("2024-04-20"));
    }
}
