package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private static final int START_INDEX = 1;

    private final ConcurrentHashMap<Long, ReservationTime> reservationTimes;
    private final AtomicLong index;

    public MemoryReservationTimeRepository() {
        this.reservationTimes = new ConcurrentHashMap<>();
        this.index = new AtomicLong(START_INDEX);
    }

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(reservationTimes.values());
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
        if (reservationTimes.containsKey(id)) {
            return Optional.of(reservationTimes.get(id));
        }
        return Optional.empty();
    }

    @Override
    public long add(final ReservationTime reservationTime) {
        final long id = index.getAndIncrement();
        reservationTimes.put(id, reservationTime);
        return id;
    }

    @Override
    public void deleteById(final Long id) {
        reservationTimes.remove(id);
    }
}
