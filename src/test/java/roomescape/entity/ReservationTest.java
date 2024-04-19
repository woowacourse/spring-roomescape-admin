package roomescape.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("두 예약을 비교, 예약 시간이 겹치는 경우를 알 수 있다")
    @Test
    void reservationTimeConflictCheckTest() {
        LocalDateTime time1 = LocalDateTime.of(2024, 4, 20, 12, 30);
        LocalDateTime time2 = LocalDateTime.of(2024, 4, 20, 13, 30);

        Reservation reservation1 = new Reservation(1L, "리비", time1.toLocalDate(), time1.toLocalTime());
        Reservation reservation2 = new Reservation(2L, "웨지", time2.toLocalDate(), time2.toLocalTime());

        assertThat(reservation1.isReservationTimeConflictWith(reservation2)).isTrue();
    }

    @DisplayName("두 예약을 비교, 예약 시간이 겹치지 않는 경우를 알 수 있다")
    @Test
    void reservationTimeNoConflictCheckTest() {
        LocalDateTime time1 = LocalDateTime.of(2024, 4, 20, 12, 30);
        LocalDateTime time2 = LocalDateTime.of(2024, 4, 20, 13, 31);

        Reservation reservation1 = new Reservation(1L, "리비", time1.toLocalDate(), time1.toLocalTime());
        Reservation reservation2 = new Reservation(2L, "웨지", time2.toLocalDate(), time2.toLocalTime());

        assertThat(reservation1.isReservationTimeConflictWith(reservation2)).isFalse();
    }
}
