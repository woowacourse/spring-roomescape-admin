package roomescape.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationMemoryRepository implements ReservationRepository {

    private final AtomicLong id = new AtomicLong(0);
    private final Map<Long, Reservation> reservations = new HashMap<>();

    @Override
    public Long add(Reservation reservation) {
        reservations.put(id.incrementAndGet(), reservation);
        return id.get();
    }

    @Override
    public void remove(Long id) {
        reservations.remove(id);
    }

    @Override
    public Map<Long, Reservation> findAllWithId() {
        return reservations;
    }
}
