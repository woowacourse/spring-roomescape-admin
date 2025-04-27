package roomescape.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class ReservationTimeTest {

    @DisplayName("예약 시간이 비어있으면 예외를 발생시킵니다.")
    @ParameterizedTest
    @NullSource
    void validateNullTimeTest(LocalTime time) {
        assertThatCode(() -> new ReservationTime(time)).isInstanceOf(IllegalArgumentException.class);
    }
}