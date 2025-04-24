package roomescape.reservationtime.dto.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.common.domain.Cacheable;
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
    public void deleteById(final long id) {
        reservationTimes.remove(id);
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
    public Long getCachedId(final Cacheable domain) {
        return 0L;
    }

    @Override
    public void cacheId(final Cacheable domain, final Long id) {

    }
}
