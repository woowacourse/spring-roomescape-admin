package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {
    @DisplayName("날짜와 시간이 동일한 경우 중복 예약으로 판단한다.")
    @Test
    void duplicateWhenDateAndTimeIsSame() {
        // given
        LocalDate date = LocalDate.of(2025, 1, 2);
        LocalTime time = LocalTime.of(10, 0);
        Reservation reservation = new Reservation("test", date, time);
        Reservation other = new Reservation("test2", date, time);

        // when
        final boolean isSame = reservation.isSameWith(other);

        // then
        assertThat(isSame).isTrue();
     }
}
