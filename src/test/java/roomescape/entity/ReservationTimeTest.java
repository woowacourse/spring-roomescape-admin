package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.entity.ReservationTime.RESERVATION_DURATION_HOUR;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTimeTest {
    @DisplayName("예약 시간이 null일 경우 생성에 실패한다")
    @Test
    void reservationTimeCreationTestWithNull() {
        assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("예약이 끝나는 시간은 시작 시간으로부터 지정된 기간 뒤 까지이다")
    @Test
    void reservationDurationTest() {
        LocalDateTime startTime = LocalDateTime.of(2024, 4, 20, 12, 30);
        ReservationTime reservationTime = new ReservationTime(startTime);

        assertThat(reservationTime.getEndDateTime()).isEqualTo(startTime.plusHours(RESERVATION_DURATION_HOUR));
    }

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
