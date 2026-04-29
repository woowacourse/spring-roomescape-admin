package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private final LocalDateTime startTime = LocalDateTime.of(2026, 4, 29, 17, 0, 0, 0);
    private final LocalDateTime endTime = LocalDateTime.of(2026, 4, 29, 18, 0, 0, 0);

    @Test
    void 다른_시간대와_겹치는_경우_예약_중복_판단_결과로_참을_반환한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(startTime, endTime);
        Reservation reservation = new Reservation("이프", reservationTime);

        // when: 17:00 ~ 18:00 (동일 시간)
        boolean result = reservation.isOverlapping(new ReservationTime(startTime, endTime));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 다른_시간대와_겹치지_않는_경우_예약_중복_판단_결과로_거짓을_반환한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(startTime, endTime);
        Reservation reservation = new Reservation("이프", reservationTime);

        // when: 18:00 ~ 19:00 (맞닿은 시간)
        boolean result = reservation.isOverlapping(new ReservationTime(endTime, endTime.plusHours(1)));

        // then
        assertThat(result).isFalse();
    }
}