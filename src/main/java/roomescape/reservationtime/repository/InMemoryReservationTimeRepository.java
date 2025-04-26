package roomescape.reservationtime.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;

@Repository
public class InMemoryReservationTimeRepository implements ReservationTimeRepository {
    private final Map<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationTime> getAll() {
        return reservationTimes.values().stream()
                .toList();
    }

    @Override
    public ReservationTime put(final ReservationTime reservationTime) {
        long id = index.getAndIncrement();
        reservationTimes.put(id, reservationTime);
        return reservationTime;
    }

    @Override
    public boolean deleteById(final long id) {
        return reservationTimes.remove(id) != null;
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        return Optional.ofNullable(reservationTimes.get(id));
    }

    @Override
    public boolean checkExistsByStartAt(final LocalTime time) {
        return reservationTimes.values().stream()
                .map(ReservationTime::getStartAt)
                .anyMatch(startAt -> startAt.equals(time));
    }

    @Override
    public Long getCachedId(final ReservationTime reservationTime) {
        return reservationTimes.entrySet().stream()
                .filter(entry -> entry.getValue().equals(reservationTime))
                .map(Entry::getKey)
                .findAny()
                .orElseThrow();
    }

    @Override
    public void cacheId(final ReservationTime reservationTime, final Long id) {
        reservationTimes.put(id, reservationTime);
    }
}
