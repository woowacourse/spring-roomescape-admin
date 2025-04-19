package roomescape.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 아이디가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(null, "밍트", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] Id는 null이 될 수 없습니다.");
    }

    @Test
    void 이름이_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(1L, null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] name은 null이 될 수 없습니다.");
    }

    @Test
    void 이름이_10글자를_넘을_경우_예외를_발생시킨다() {
        final String name = "잠실에사는비행기데코피크민";
        Assertions.assertThatThrownBy(() -> new Reservation(1L, name, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이름은 10글자를 넘을 수 없습니다.");
    }

    @Test
    void 날짜가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] dateTime은 null이 될 수 없습니다.");
    }

    @Test
    void 과거_날짜인_경우_예외를_발생시킨다() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", yesterday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 과거 날짜로 예약할 수 없습니다.");
    }
}
