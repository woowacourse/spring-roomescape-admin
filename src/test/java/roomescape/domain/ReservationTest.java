package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private final LocalDate date = LocalDate.now();
    private final ReservationTime time = new ReservationTime(1L, LocalTime.now());

    @Test
    void 이름이_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(1L, null, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] name은 null이 될 수 없습니다.");
    }

    @Test
    void 이름이_10글자를_넘을_경우_예외를_발생시킨다() {
        final String name = "잠실에사는비행기데코피크민";
        Assertions.assertThatThrownBy(() -> new Reservation(1L, name, date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이름은 10글자를 넘을 수 없습니다.");
    }

    @Test
    void 날짜가_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", null, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 날짜는 null이 될 수 없습니다.");
    }

    @Test
    void 과거_날짜인_경우_예외를_발생시킨다() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", yesterday, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 과거 날짜로 예약할 수 없습니다.");
    }

    @Test
    void 시간이_null인_경우_예외를_발생시킨다() {
        Assertions.assertThatThrownBy(() -> new Reservation(2L, "메이", date, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 시간은 null이 될 수 없습니다.");
    }
}
