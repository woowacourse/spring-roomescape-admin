package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeRepositoryImpl implements FakeReservationTimeRepository {
    private final Map<Long, ReservationTime> sources = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1L);

    @Override
    public void clear() {
        sources.clear();
        id.set(1L);
    }

    @Override
    public long save(final ReservationTime reservationTime) {
        long id = this.id.getAndIncrement();
        reservationTime.setId(id);
        sources.put(id, reservationTime);

        return id;
    }

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(sources.values());
    }

    @Override
    public void deleteById(long id) {
        sources.remove(id);
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return Optional.ofNullable(sources.get(id));
    }
}
