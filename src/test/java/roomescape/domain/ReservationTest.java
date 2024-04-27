package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void validateId() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        Assertions.assertThatThrownBy(() -> new Reservation(null, "브라운", LocalDate.of(2024, 4, 28), reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] id값이 존재하지 않습니다.");
    }
}
