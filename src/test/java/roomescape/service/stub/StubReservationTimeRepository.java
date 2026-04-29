package roomescape.service.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.time.entity.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

public class StubReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> store = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(long id) {
        return store.containsKey(id);
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        long id = index.getAndIncrement();
        ReservationTime saved = ReservationTime.of(id, reservationTime.getStartAt());
        store.put(id, saved);
        return saved;
    }
}