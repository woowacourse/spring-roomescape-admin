package roomescape.domain.fixture;

import java.util.List;
import roomescape.domain.Reservations;

public class ReservationsFixture {

    public static Reservations createEmptyReservations() {
        return new Reservations(List.of());
    }
}
