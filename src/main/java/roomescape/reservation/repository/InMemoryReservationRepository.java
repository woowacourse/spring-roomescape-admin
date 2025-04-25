package roomescape.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
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
    public Long getCachedId(final Reservation reservation) {
        return reservations.entrySet().stream()
                .filter(entry -> entry.getValue().equals(reservation))
                .map(Entry::getKey)
                .findAny()
                .orElseThrow();
    }

    @Override
    public void cacheId(final Reservation reservation, final Long id) {
        reservations.put(id, reservation);
    }
}
