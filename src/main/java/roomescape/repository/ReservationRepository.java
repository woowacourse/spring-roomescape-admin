package roomescape.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class ReservationRepository {

    private final AtomicLong id = new AtomicLong(0);
    private final Map<Long, Reservation> reservations = new HashMap<>();

    public Long add(Reservation reservation) {
        reservations.put(id.incrementAndGet(), reservation);
        return id.get();
    }

    public void remove(Long id) {
        reservations.remove(id);
    }

    public Map<Long, Reservation> findAllWithId() {
        return reservations;
    }
}
