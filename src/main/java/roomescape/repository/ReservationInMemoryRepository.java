package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private final Map<Long, Reservation> sources = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public long save(final Reservation reservation) {
        sources.put(id.getAndIncrement(), reservation);
        return id.get();
    }

    @Override
    public List<Reservation> findAll() {
        return sources.values().stream().toList();
    }
}
