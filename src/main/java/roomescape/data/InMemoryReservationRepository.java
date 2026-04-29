package roomescape.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

@Component
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong incrementId = new AtomicLong(0);
    private final Map<Long, Reservation> storage = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        long nextId = incrementId.incrementAndGet();
        Reservation saved = new Reservation(nextId, reservation.getCustomerName(), reservation.getReservationTime());
        storage.put(nextId, saved);
        return saved;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(storage.values());
    }
}
