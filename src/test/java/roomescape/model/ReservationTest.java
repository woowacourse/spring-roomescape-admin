package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @DisplayName("예약 내역의 속성에 널 값을 입력한 경우 예외 처리한다")
    @Test
    void throwException_NullValue() {
        LocalDate date = LocalDate.parse("2025-04-17");
        LocalTime time = LocalTime.parse("19:00");

        assertThatThrownBy(() -> new Reservation(null, date, new ReservationTime(1L, time)))
                .isInstanceOf(NullPointerException.class);
    }
}
