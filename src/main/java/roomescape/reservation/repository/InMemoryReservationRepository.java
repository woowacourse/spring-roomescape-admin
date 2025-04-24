package roomescape.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.common.domain.Cacheable;
import roomescape.common.repository.AbstractRepository;
import roomescape.reservation.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements AbstractRepository<Reservation> {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> getAll() {
        return reservations.values().stream()
                .toList();
    }

    @Override
    public Reservation put(final Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.put(id, reservation);
        return reservation;
    }

    @Override
    public void deleteById(final long id) {
        reservations.remove(id);
    }

    @Override
    public Optional<Reservation> findById(final long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Long getCachedId(final Cacheable domain) {
        return 0L;
    }

    @Override
    public void cacheId(final Cacheable domain, final Long id) {

    }
}
