package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReservationTimeTest {

    private final LocalDateTime startTime = LocalDateTime.of(2026, 4, 29, 17, 0, 0, 0);
    private final LocalDateTime endTime = LocalDateTime.of(2026, 4, 29, 18, 0, 0, 0);

    @Test
    void 예약_시간이_겹치게_되는_경우_참을_반환한다() {
        // given: 17:00 ~ 18:00
        ReservationTime target = new ReservationTime(startTime, endTime);

        // when: 17:30 ~ 18:30 (걸쳐 있는 경우)
        ReservationTime other = new ReservationTime(startTime.plusMinutes(30), endTime.plusMinutes(30));

        // then
        assertThat(target.isOverlapping(other)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "16, 17", // 기존 시간 직전에 종료
            "18, 19"  // 기존 시간 직후에 시작
    })
    void 예약_시간이_겹치지_않는_경우_거짓을_반환한다(int startHour, int endHour) {
        // given: 17:00 ~ 18:00
        ReservationTime target = new ReservationTime(startTime, endTime);

        // when
        LocalDateTime start = LocalDateTime.of(2026, 4, 29, startHour, 0);
        LocalDateTime end = LocalDateTime.of(2026, 4, 29, endHour, 0);
        ReservationTime other = new ReservationTime(start, end);

        // then
        assertThat(target.isOverlapping(other)).isFalse();
    }
}