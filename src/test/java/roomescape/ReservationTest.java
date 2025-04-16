package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReservationTest {

    @DisplayName("예약을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void createTest(int differentSecond) {
        // given
        int id = 1;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusSeconds(differentSecond);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(id, name, reservationDate, reservationTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 일시로 예약을 생성할 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void shouldThrowException_WhenCreatePastReservation(int differentSecond) {
        // given
        int id = 1;
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().minusSeconds(differentSecond);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(id, name, reservationDate, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거 일시로 예약을 생성할 수 없습니다.");
    }
}
