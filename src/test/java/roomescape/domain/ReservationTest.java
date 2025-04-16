package roomescape.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void 아이디가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(null, "밍트", LocalDateTime.now()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Id는 null이 될 수 없습니다.");
    }

    @Test
    void 이름이_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(1L, null, LocalDateTime.now()))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Name은 null이 될 수 없습니다.");
    }

    @Test
    void 날짜가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("DateTime은 null이 될 수 없습니다.");
    }
}
