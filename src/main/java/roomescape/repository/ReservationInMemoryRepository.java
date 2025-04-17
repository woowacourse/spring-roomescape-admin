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
        long id = this.id.getAndIncrement();
        reservation.setId(id);
        sources.put(id, reservation);

        return this.id.get();
    }

    @Override
    public List<Reservation> findAll() {
        return sources.values().stream().toList();
    }

    @Override
    public void deleteById(final long id) {
        if (!sources.containsKey(id)) {
            throw new IllegalArgumentException("id에 해당하는 예약 내역이 없습니다.");
        }
        sources.remove(id);
    }
}
