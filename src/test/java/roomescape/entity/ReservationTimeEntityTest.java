package roomescape.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTimeEntityTest {
    @DisplayName("게임 러닝 타임(2시간)을 고려하여 예약 중복 여부를 판단할 수 있다.")
    @Test
    void duplicateByRunningTime() {
        // given
        LocalTime time = LocalTime.of(10, 0);
        LocalTime duplicateTime = time.plusHours(1);
        ReservationTimeEntity timeEntity = new ReservationTimeEntity(1L, time);
        ReservationTimeEntity otherTimeEntity = new ReservationTimeEntity(2L, duplicateTime);

        // when
        final boolean isDuplicated = timeEntity.isDuplicatedWith(otherTimeEntity);

        // then
        assertThat(isDuplicated).isTrue();
    }

    @DisplayName("게임 러닝 타임(2시간) 이후의 예약은 중복이 아니다.")
    @Test
    void notDuplicateByRunningTime() {
        // given
        LocalTime time = LocalTime.of(10, 0);
        LocalTime duplicateTime = time.plusHours(2);
        ReservationTimeEntity timeEntity = new ReservationTimeEntity(1L, time);
        ReservationTimeEntity otherTimeEntity = new ReservationTimeEntity(2L, duplicateTime);

        // when
        final boolean isDuplicated = timeEntity.isDuplicatedWith(otherTimeEntity);

        // then
        assertThat(isDuplicated).isFalse();
    }
}
