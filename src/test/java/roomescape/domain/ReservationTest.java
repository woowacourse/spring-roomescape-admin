package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    @DisplayName("정상적인 값을 입력하면 예약 객체가 생성된다.")
    void create_ValidParameters_CreatesReservation() {
        Reservation reservation = new Reservation(1L, "브라운", LocalDate.now().plusDays(1), 1L);
        assertThat(reservation.name()).isEqualTo("브라운");
    }

    @Test
    @DisplayName("예약자 이름이 null이거나 비어있으면 예외가 발생한다.")
    void create_InvalidName_ThrowsException() {
        assertThatThrownBy(() -> new Reservation(1L, " ", LocalDate.now().plusDays(1), 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 날짜가 과거 날짜이면 예외가 발생한다.")
    void create_PastDate_ThrowsException() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        assertThatThrownBy(() -> new Reservation(1L, "브라운", pastDate, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약 시간 식별자가 유효하지 않으면 예외가 발생한다.")
    void create_InvalidTimeId_ThrowsException() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", LocalDate.now().plusDays(1), 0L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("transientOf를 통해 비영속 상태의 예약 객체를 생성할 수 있다.")
    void transientOf_ValidParameters_CreatesTransientReservation() {
        Reservation reservation = Reservation.transientOf("브라운", LocalDate.now().plusDays(1), 1L);
        assertThat(reservation.id()).isNull();
    }
}
