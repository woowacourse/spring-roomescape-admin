package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTimeTest {

    @DisplayName("예약 시간 객체를 생성할 수 있다")
    @Test
    void createReservationTime() {
        assertThatCode(() -> new ReservationTime(1L, LocalTime.parse("10:00"))).doesNotThrowAnyException();
    }
}
