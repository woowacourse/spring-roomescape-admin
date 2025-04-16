package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    @Test
    void 예약_추가_실패() {
        // given
        final Reservations reservations = new Reservations();
        final Long id = 1L;
        final Reservation reservation = new Reservation(id, "테스트2", LocalDate.of(2026, 4, 16), LocalTime.of(13, 44));

        // when
        reservations.add(new Reservation(id, "테스트", LocalDate.of(2025, 4, 16), LocalTime.of(12, 44)));

        // then
        assertThatThrownBy(() -> reservations.add(reservation)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 예약_추가_성공() {
        // given
        final Reservations reservations = new Reservations();
        final Reservation reservation = new Reservation(2L, "테스트2", LocalDate.of(2026, 4, 16), LocalTime.of(13, 44));

        // when
        reservations.add(new Reservation(1L, "테스트", LocalDate.of(2025, 4, 16), LocalTime.of(12, 44)));

        // then
        assertThatCode(() -> reservations.add(reservation)).doesNotThrowAnyException();
    }
}
