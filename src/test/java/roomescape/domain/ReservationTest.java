package roomescape.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

        static String NAME = "브라운";
        static String DATE = "2023-08-05";
        static String TIME = "15:40";

        @DisplayName("모든 값이 정상 이면 방 탈출 예약 성공")
        @Test
        void reservation_fail_by_date_format() {
            assertDoesNotThrow(() -> new Reservation(NAME, DATE, TIME));
        }

        @DisplayName("방 탈출 예약 성공 시 날짜, 시간 정상 변환 성공 여부 확인 테스트")
        @Test
        void check_reservation_date_and_time_if_success() {
            Reservation reservation = new Reservation(NAME, DATE, TIME);

            LocalDate reservationDate = reservation.getDate();
            LocalTime reservationTime = reservation.getTime();

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(reservationDate).isEqualTo(LocalDate.of(2023, 8, 5));
                softly.assertThat(reservationTime).isEqualTo(LocalTime.of(15, 40));
            });
        }
    }
}
