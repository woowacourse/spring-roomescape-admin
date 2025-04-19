package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationsTest {

    private final Reservations reservations = new Reservations();

    @Test
    void 예약을_추가한다() {
        // Given
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        // When & Then
        Assertions.assertThatCode(() -> {
            reservations.addReservation("밍트", tomorrow);
        }).doesNotThrowAnyException();
    }

    @Test
    void 예약을_삭제한다() {
        // Given
        final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        reservations.addReservation("밍트", tomorrow);

        // When
        reservations.deleteById(1L);

        // Then
        assertThat(reservations.getReservations()).isEmpty();
    }
}
