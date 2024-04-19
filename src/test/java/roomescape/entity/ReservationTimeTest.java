package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @DisplayName("다른 예약시간과 비교, 예약 시간이 겹치는 경우를 알 수 있다")
    @Test
    void reservationTimeConflictCheckTest() {
        LocalDateTime time1 = LocalDateTime.of(2024, 4, 20, 12, 30);
        LocalDateTime time2 = LocalDateTime.of(2024, 4, 20, 13, 30);

        ReservationTime reservationTime = new ReservationTime(time1);
        ReservationTime conflictTime = new ReservationTime(time2);

        assertThat(reservationTime.isConflictWith(conflictTime)).isTrue();
    }

    @DisplayName("다른 예약시간과 비교, 예약 시간이 겹치지 않는 경우를 알 수 있다")
    @Test
    void reservationTimeNoConflictCheckTest() {
        LocalDateTime time1 = LocalDateTime.of(2024, 4, 20, 12, 30);
        LocalDateTime time2 = LocalDateTime.of(2024, 4, 20, 13, 31);

        ReservationTime reservationTime = new ReservationTime(time1);
        ReservationTime nonConflictTime = new ReservationTime(time2);

        assertThat(reservationTime.isConflictWith(nonConflictTime)).isFalse();
    }
}
