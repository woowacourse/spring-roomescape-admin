package roomescape.domain.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTimeTest {
    @DisplayName("예약 시간 객체를 생성한다.")
    @Test
    void 객체_정상_생성_테스트() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        // when & then
        assertThat(time.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("시작 시간이 null인 경우, IllegalArgumentException이 발생한다.")
    @Test
    void 시작_시간_null_예외_테스트() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작 시간은 비어 있을 수 없습니다.");
    }
}
