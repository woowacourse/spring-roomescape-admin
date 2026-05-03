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
        ReservationTime time = ReservationTime.from(1L, LocalTime.of(10, 0));

        assertThatThrownBy(() -> Reservation.createNew("", LocalDate.now().plusDays(1), time, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수입니다.");
    }

    @Test
    @DisplayName("정상적인 값으로 객체가 생성된다.")
    void create_Success() {
        ReservationTime time = ReservationTime.from(1L, LocalTime.of(10, 0));
        LocalDate today = LocalDate.now();

        assertThatCode(() -> Reservation.from(1L, "브라운", today, time))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("과거 날짜 데이터로 객체를 생성할 때는 예외가 발생하지 않는다.")
    void validateDate_Past_Success() {
        ReservationTime time = ReservationTime.from(1L, LocalTime.of(10, 0));
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertThatCode(() -> Reservation.from(1L, "브라운", pastDate, time))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("현재 날짜보다 과거의 날짜로 예약을 생성하면 예외가 발생한다.")
    void createNew_PastDate() {
        // given
        LocalDate currentDate = LocalDate.of(2026, 5, 3);
        LocalDate pastDate = LocalDate.of(2026, 5, 2);
        ReservationTime time = ReservationTime.from(1L, LocalTime.of(10, 0));

        // when & then
        assertThatThrownBy(() -> Reservation.createNew("브라운", pastDate, time, currentDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지나간 날짜는 예약할 수 없습니다.");
    }
}
