package roomescape.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static roomescape.fixture.DateTimeFixture.DAY_AFTER_TOMORROW;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_ID_0300;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {
    @DisplayName("예약이 끝나는 시간을 계산할 수 있다")
    @Test
    void calculateReservationEndTimeTest() {
        Reservation reservation = new Reservation("리비", DAY_AFTER_TOMORROW, GAME_TIME_WITH_ID_0300);
        LocalTime expectEndTime = LocalTime.of(3, 0).plusHours(Reservation.TIME_DURATION);
        assertThat(reservation.getEndDateTime().toLocalTime()).isEqualTo(expectEndTime);
    }
}
