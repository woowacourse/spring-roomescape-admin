package roomescape.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Reservations {

    private final AtomicLong autoIncrementId = new AtomicLong(0L);
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public List<Reservation> getAll() {
        return reservations.values().stream().toList();
    }

    public Reservation save(Reservation reservation) {
        Reservation saved = reservation.withId(autoIncrementId.incrementAndGet());
        reservations.put(saved.id(), saved);
        return saved;
    }

    public void remove(Long id) {
        reservations.remove(id);
    }
}
