package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("이름이 비어있으면 예외가 발생한다.")
    void validateName() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> new Reservation(1L, "", LocalDate.now(), time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수입니다.");
    }

    @Test
    @DisplayName("과거 날짜로 예약을 생성하면 예외가 발생한다.")
    void validateDate_Past() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertThatThrownBy(() -> new Reservation(1L, "브라운", pastDate, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거 날짜는 예약할 수 없습니다.");
    }

    @Test
    @DisplayName("정상적인 값으로 객체가 생성된다.")
    void create_Success() {
        ReservationTime time = new ReservationTime(1L, LocalTime.of(10, 0));
        LocalDate today = LocalDate.now();

        assertThatCode(() -> new Reservation(1L, "브라운", today, time))
                .doesNotThrowAnyException();
    }
}
