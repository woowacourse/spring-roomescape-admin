package roomescape;

import static org.junit.jupiter.api.Assertions.assertAll;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservationsTest {
    @Test
    void constructor() {
        // when
        Reservations reservations = new Reservations();

        // then
        assertAll(
                () -> Assertions.assertThat(reservations.getReservations().size()).isEqualTo(0),
                () -> Assertions.assertThat(reservations).isInstanceOf(Reservations.class)
        );
    }

    @Test
    void add() {
        // given
        Reservations reservations = new Reservations();

        // when
        reservations.add(new Reservation(0L, null, null, null));

        // then
        Assertions.assertThat(reservations.getReservations().size()).isEqualTo(1);
    }

    @Test
    void remove() {
        // given
        Reservations reservations = new Reservations();
        reservations.add(new Reservation(0L, null, null, null));

        // when
        reservations.remove(0L);

        // then
        Assertions.assertThat(reservations.getReservations().size()).isEqualTo(0);
    }
}
