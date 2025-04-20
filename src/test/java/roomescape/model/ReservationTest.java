package roomescape.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.exception.ReservationException;

class ReservationTest {

    @DisplayName("예약을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void createTest(int differentSecond) {
        // given
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().plusSeconds(differentSecond);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(name, reservationDate, reservationTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("과거 일시로 예약을 생성할 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 60, 3_600, 86_400})
    void shouldThrowException_WhenCreatePastReservation(int differentSecond) {
        // given
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.now().minusSeconds(differentSecond);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(name, reservationDate, reservationTime))
                .isInstanceOf(ReservationException.class)
                .hasMessage("과거 일시로 예약을 생성할 수 없습니다.");
    }

    @DisplayName("예약자명 1자 이상 20자 이하가 아닌 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "이 문자열은 21자로 구성되어있습니다."})
    void shouldThrowException_WhenCreateWithEmptyName(String invalidName) {
        // given
        LocalDateTime reservationDateTime = LocalDateTime.now().plusHours(1);
        LocalDate reservationDate = reservationDateTime.toLocalDate();
        LocalTime reservationTime = reservationDateTime.toLocalTime();

        // when & then
        assertThatCode(() -> new Reservation(invalidName, reservationDate, reservationTime))
                .isInstanceOf(ReservationException.class)
                .hasMessage("예약자명은 1자 이상 20자 이하로만 가능합니다.");
    }
}
