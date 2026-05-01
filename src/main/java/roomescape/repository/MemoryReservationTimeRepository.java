package roomescape.repository;

import roomescape.domain.ReservationTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> store = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return store.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public ReservationTime save(ReservationTime time) {
        Long id = index.incrementAndGet();
        ReservationTime saved = new ReservationTime(id, time.getStartAt());
        store.add(saved);
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        store.removeIf(t -> t.getId().equals(id));
    }
}
