package roomescape.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static roomescape.fixture.DateTimeFixture.DATE_2024_04_20;
import static roomescape.fixture.DateTimeFixture.DATE_2024_04_20_TIME_03_00;
import static roomescape.fixture.DateTimeFixture.TIME_03_00_WITH_ID;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    @DisplayName("예약이 끝나는 시간을 계산할 수 있다")
    @Test
    void calculateReservationEndTimeTest() {
        LocalDateTime dateTime = DATE_2024_04_20_TIME_03_00;
        Reservation reservation = new Reservation("리비", DATE_2024_04_20, TIME_03_00_WITH_ID);

        assertThat(reservation.getEndDateTime()).isEqualTo(dateTime.plusHours(Reservation.TIME_DURATION));
    }
}
