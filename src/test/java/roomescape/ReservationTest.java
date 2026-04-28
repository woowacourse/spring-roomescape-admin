package roomescape;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.swing.text.Position;
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

        @DisplayName("시간 형식 안 맞으면 방 탈출 예약 실패")
        @Test
        void reservation_fail_by_time_format() {
            String name = "브라운";
            String date = "2023-08-05";
            String time = "1540";

            Assertions.assertThatThrownBy(() -> new Reservation(name, date, time))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("정상 상황")
    @Nested
    class Success {
        @DisplayName("모든 값이 정상 이면 방 탈출 예약 성공")
        @Test
        void reservation_fail_by_date_format() {
            String name = "브라운";
            String date = "2023-08-05";
            String time = "15:40";

            assertDoesNotThrow(() -> new Reservation(name, date, time));
        }
    }
}
