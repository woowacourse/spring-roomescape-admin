package roomescape;

import static org.junit.jupiter.api.Assertions.assertAll;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationsTest {
    @Test
    void constructor() {
        // when
        Reservations reservations = new Reservations();

        // then
        assertAll(
                () -> Assertions.assertThat(reservations.getReservations()).hasSize(0),
                () -> Assertions.assertThat(reservations).isInstanceOf(Reservations.class)
        );
    }

    @Test
    void add() {
        // given
        Reservations reservations = new Reservations();

        // when
        reservations.add(new Reservation(0L, "도기", LocalDate.now(), LocalTime.now()));

        // then
        Assertions.assertThat(reservations.getReservations()).hasSize(1);
    }

    @Test
    void remove() {
        // given
        Reservations reservations = new Reservations();
        reservations.add(new Reservation(0L, "히스타", LocalDate.now(), LocalTime.now()));

        // when
        reservations.remove(0L);

        // then
        Assertions.assertThat(reservations.getReservations()).isEmpty();
    }

    @DisplayName("일치하는 ID의 예약을 찾을 수 없을 때 .remove()의 예외 발생 테스트")
    @Test
    void removeNotExistId() {
        // given
        Reservations reservations = new Reservations();
        reservations.add(new Reservation(0L, "이프", LocalDate.now(), LocalTime.now()));

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                () -> reservations.remove(1L)
        );
    }
}
