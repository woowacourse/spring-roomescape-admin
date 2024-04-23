package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Reservation(1L, "wiib", LocalDate.now(), new ReservationTime(1L, LocalTime.now())))
                .doesNotThrowAnyException();
        assertThatCode(() -> new Reservation("wiib", LocalDate.now(), new ReservationTime(1L, LocalTime.now())))
                .doesNotThrowAnyException();

    }

    @DisplayName("현재 시간대보다 이전 시간대는 예약을 할 수 없다.")
    @Test
    void createFail() {
        assertThatCode(() -> new Reservation("wiib", LocalDate.MIN, new ReservationTime(LocalTime.MIN)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
