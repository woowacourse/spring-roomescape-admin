package roomescape.console.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.model.Reservation;
import roomescape.core.repository.ReservationRepository;

public class ReservationMemoryRepository implements ReservationRepository {
    private static final int ZERO_RESERVATION = 0;
    private static final int ONE_RESERVATION = 1;
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    @Override
    public Long createReservation(Reservation reservation) {
        reservations.put(id.incrementAndGet(), reservation);
        return id.longValue();
    }

    @Override
    public List<Reservation> readReservations() {
        return reservations.values().stream().toList();
    }

    @Override
    public Reservation readReservationById(Long id) {
        return reservations.get(id);
    }

    @Override
    public int deleteReservation(Long id) {
        if (reservations.remove(id) == null) {
            return ZERO_RESERVATION;
        }
        return ONE_RESERVATION;
    }
}
