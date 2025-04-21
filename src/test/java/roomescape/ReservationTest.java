package roomescape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {
    @DisplayName("날짜와 시간이 동일한 경우 중복 예약으로 판단한다.")
    @Test
    void duplicateWhenDateAndTimeIsSame() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 2, 10, 0);
        Reservation reservation = Reservation.of("test", dateTime);
        Reservation other = Reservation.of("test2", dateTime);

        // when
        final boolean isSame = reservation.isDuplicatedWith(other);

        // then
        assertThat(isSame).isTrue();
     }

    @DisplayName("기존 예약의 (시작 시간)과 (시작 시간 + 2시간) 사이에 예약된 것은 중복 예약으로 판단한다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource
    void duplicateWhenBetweenStartAndEnd(String description, LocalDateTime otherDateTime, boolean expected) {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 2, 10, 0);
        Reservation reservation = Reservation.of("test", dateTime);
        Reservation other = Reservation.of("test2", otherDateTime);

        // when
        final boolean isDuplicated = reservation.isDuplicatedWith(other);

        // then
        assertThat(isDuplicated).isSameAs(expected);
    }

    private static Stream<Arguments> duplicateWhenBetweenStartAndEnd() {
        return Stream.of(
                Arguments.of(
                        "예약 시간이 기존 예약의 시작 시간 ~ 종료 시간 사이에 있는 경우 중복으로 판단한다.",
                        LocalDateTime.of(2025, 1, 2, 11, 59),
                        true
                ),
                Arguments.of(
                        "예약 시간이 기존 예약의 종료 시간과 같은 경우 중복으로 판단하지 않는다.",
                        LocalDateTime.of(2025, 1, 2, 12, 0),
                        false
                ),
                Arguments.of(
                        "예약 시간이 기존 예약의 시작 시간과 같은 경우 중복으로 판단한다.",
                        LocalDateTime.of(2025, 1, 2, 10, 0),
                        true
                )
        );
    }
}
