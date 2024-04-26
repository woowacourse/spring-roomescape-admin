package roomescape.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static roomescape.fixture.DateTimeFixture.DAY_AFTER_TOMORROW;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_ID_0300;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_NO_ID_0300;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_NO_ID_0400;

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

    @DisplayName("특정 예약과 시간이 충돌하는지 알 수 있다")
    @Test
    void reservationConflictCheckTest() {
        Reservation reservation = new Reservation("리비", DAY_AFTER_TOMORROW, GAME_TIME_WITH_ID_0300);
        Reservation conflictReservation = new Reservation("웨지", DAY_AFTER_TOMORROW, GAME_TIME_WITH_ID_0300);
        assertThat(reservation.isConflictWith(conflictReservation)).isTrue();
    }

    @DisplayName("4시에 끝나는 예약과 4시에 시작하는 예약은 충돌한다")
    @Test
    void reservationNonConflictCheckTest() {
        Reservation reservation = new Reservation("리비", DAY_AFTER_TOMORROW, GAME_TIME_WITH_NO_ID_0300);
        Reservation nonConflict = new Reservation("웨지", DAY_AFTER_TOMORROW, GAME_TIME_WITH_NO_ID_0400);
        assertThat(reservation.isConflictWith(nonConflict)).isTrue();
    }
}
