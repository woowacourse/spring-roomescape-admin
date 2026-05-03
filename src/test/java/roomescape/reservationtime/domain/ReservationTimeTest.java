package roomescape.reservationtime.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTimeTest {
    @ParameterizedTest
    @ValueSource(strings = {"09:59", "22:01"})
    @DisplayName("영업 시간(10시~22시) 범위 밖의 시간으로 생성 시 예외가 발생한다")
    void 영업_시간_범위_밖_예외_발생(String time) {
        LocalTime invalidTime = LocalTime.parse(time);

        assertThatThrownBy(() -> ReservationTime.builder()
                .id(1L)
                .startAt(invalidTime)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"10:00", "22:00"})
    @DisplayName("영업 시간(10시~22시) 범위 안에 걸치는 시간은 정상적으로 생성된다")
    void 영업_시간_경계값_생성_성공(String time) {
        LocalTime boundaryTime = LocalTime.parse(time);

        ReservationTime reservationTime = ReservationTime.builder()
                .id(1L)
                .startAt(boundaryTime)
                .build();

        assertThat(reservationTime.getStartAt()).isEqualTo(boundaryTime);
    }
}
