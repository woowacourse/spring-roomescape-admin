package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReservationTest {

    private final ReservationTime validTime = new ReservationTime(1L, LocalTime.of(10, 0));

    @Test
    @DisplayName("이름이 null이면 예외가 발생한다")
    void nameNull() {
        assertThatThrownBy(() -> new Reservation(null, null, LocalDate.now().plusDays(1), validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 필수입니다.");
    }

    @Test
    @DisplayName("이름이 공백이면 예외가 발생한다")
    void nameBlank() {
        assertThatThrownBy(() -> new Reservation(null, "  ", LocalDate.now().plusDays(1), validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약자 이름은 필수입니다.");
    }

    @Test
    @DisplayName("날짜가 null이면 예외가 발생한다")
    void dateNull() {
        assertThatThrownBy(() -> new Reservation(null, "가현", null, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 필수입니다.");
    }

    @Test
    @DisplayName("시간이 null이면 예외가 발생한다")
    void timeNull() {
        assertThatThrownBy(() -> new Reservation(null, "가현", LocalDate.now().plusDays(1), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 필수입니다.");
    }

    @Test
    @DisplayName("유효한 값으로 예약을 생성할 수 있다")
    void createSuccess() {
        Reservation reservation = new Reservation(null, "가현", LocalDate.now().plusDays(1), validTime);

        assertThat(reservation.getName()).isEqualTo("가현");
    }

    @Test
    @DisplayName("이미 지난 날짜와 시간으로 예약을 생성하면 예외가 발생한다")
    void pastDatetime() {
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertThatThrownBy(() -> Reservation.create(null, "가현", pastDate, validTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 지난 날짜와 시간으로 예약할 수 없습니다.");
    }

    @Test
    @DisplayName("미래 날짜로 예약을 생성할 수 있다")
    void futureDatetime() {
        LocalDate futureDate = LocalDate.now().plusDays(1);

        Reservation reservation = Reservation.create(null, "가현", futureDate, validTime);

        assertThat(reservation.getName()).isEqualTo("가현");
    }
}