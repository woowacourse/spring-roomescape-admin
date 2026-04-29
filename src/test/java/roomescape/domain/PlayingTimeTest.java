package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayingTimeTest {

    @Test
    void 시작_시간이_주어지면_플레이_시간을_더해_종료_시간이_포함된_예약시간을_생성한다() {
        // given: 60분짜리 플레이 시간 객체
        int minutes = 100;
        PlayingTime playingTime = PlayingTime.ofMinutes(minutes);
        LocalDateTime startTime = LocalDateTime.of(2026, 4, 29, 14, 0);

        // when: 예약 시간대 계산
        ReservationTime result = playingTime.calculateReservationTime(startTime);

        // then: 시작은 14:00, 종료는 15:00여야 함
        assertThat(result.startTime()).isEqualTo(startTime);
        assertThat(result.endTime()).isEqualTo(startTime.plusMinutes(minutes));
    }

    @Test
    @DisplayName("기본 플레이 시간은 60분으로 설정되어 있다.")
    void 기본_플레이_시간은_60분으로_설정되어_있다() {
        // given
        PlayingTime defaultTime = PlayingTime.toDefaultPlayingTime();
        LocalDateTime startTime = LocalDateTime.of(2026, 4, 29, 19, 0);

        // when
        ReservationTime result = defaultTime.calculateReservationTime(startTime);

        // then
        assertThat(result.endTime()).isEqualTo(startTime.plusHours(1));
    }
}