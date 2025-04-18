package roomescape;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    final Reservations reservations = new Reservations();
    final Long id = 1L;
    final Reservation reservation = new Reservation(id, "테스트2", LocalDate.of(2026, 4, 16), LocalTime.of(13, 44));

    @BeforeEach
    void setUp() {
        reservations.add(reservation);
    }

    @Test
    void 동일_ID_예약_추가_시_예외_발생() {
        // given
        // when

        // then
        assertThatThrownBy(() -> reservations.add(reservation)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 다른_ID_예약_추가_성공() {
        // given
        final Reservation reservationTest = new Reservation(2L, "테스트", LocalDate.of(2025, 4, 16), LocalTime.of(12, 44));

        // when
        // then
        assertThatCode(() -> reservations.add(reservationTest)).doesNotThrowAnyException();
    }
}
