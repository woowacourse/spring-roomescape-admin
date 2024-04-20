package roomescape.reservation.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.reservation.Reservation;

public class FakeReservationDao implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();

    @Override
    public long save(final Reservation reservation) {
        reservations.put((long) reservations.size() + 1, reservation);
        return reservations.size();
    }
}
