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
    @DisplayName("DB에서 조회한 과거 날짜 데이터로 객체를 생성할 때는 예외가 발생하지 않는다.")
    void validateDate_Past_Success() {
        ReservationTime time = new ReservationTime(1L, java.time.LocalTime.of(10, 0));
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertThatCode(() -> new Reservation(1L, "브라운", pastDate, time))
                .doesNotThrowAnyException();
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
