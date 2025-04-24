package roomescape.entity;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

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

    @Disabled
    @DisplayName("예약 가능한 운영 시간은 10:00 ~ 22:00 이다.")
    @ParameterizedTest
    @MethodSource
    void validOperatingTime(LocalTime reservationTime) {
        // given

        // when & then
        assertThatCode(() -> {
            new ReservationTimeEntity(1L, reservationTime);
        }).doesNotThrowAnyException();
    }

    private static Stream<Arguments> validOperatingTime() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 0)),
                Arguments.of(LocalTime.of(21, 59))
        );
    }

    @Disabled
    @DisplayName("운영 시간 이외에는 예약할 수 없다.")
    @ParameterizedTest
    @MethodSource
    void invalidOperatingTime(LocalTime reservationTime) {
        // given

        // when & then
        assertThatThrownBy(() -> {
            new ReservationTimeEntity(1L, reservationTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> invalidOperatingTime() {
        return Stream.of(
                Arguments.of(LocalTime.of(9, 59)),
                Arguments.of(LocalTime.of(22, 1))
        );
    }
}
