package roomescape;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    @DisplayName("예외 상황")
    @Nested
    class Failure {
        @DisplayName("날짜 형식 안 맞으면 방 탈출 예약 실패")
        @Test
        void reservation_fail_by_date_format() {
            String name = "브라운";
            String date = "2023-0805";
            String time = "15:40";

            Assertions.assertThatThrownBy(() -> new Reservation(name, date, time))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
