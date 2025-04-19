package roomescape.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ReservationTest {

    @Test
    void 아이디가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(null, "밍트", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 예약 기록 id는 null이 될 수 없습니다.");
    }

    @Test
    void 이름이_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(1L, null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 예약자 이름은 null이 될 수 없습니다.");
    }

    @Test
    void 이름이_10글자_초과인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(1L, "잠실에사는비행기데코피크민", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 예약자 이름은 10글자를 초과할 수 없습니다.");
    }

    @Test
    void 날짜가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 과거 날짜에 대한 예약을 할 수 없습니다.");
    }

    @Test
    void 날짜가_과거인_경우_예외를_발생시킨다() {
        // given
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", yesterday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 과거 날짜에 대한 예약을 할 수 없습니다.");
    }
}
