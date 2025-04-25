package roomescape.user.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.user.domain.Reservation;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    public Long save(final Reservation reservation) {
        final Long currentId = id.getAndIncrement();
        final Reservation created = new Reservation(
                currentId, reservation.getName(), reservation.getDate(), reservation.getTime());

        reservations.put(currentId, created);

        return currentId;
    }

    public Optional<Reservation> findById(final Long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    public void deleteById(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalStateException("Reservation with id " + id + " does not exist");
        }

        reservations.remove(id);
    }

    public void clear() {
        reservations.clear();
    }
}
