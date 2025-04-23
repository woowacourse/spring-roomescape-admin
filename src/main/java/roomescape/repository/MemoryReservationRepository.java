package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final MemoryReservationTimeRepository timeRepository;
    private final ConcurrentHashMap<Long, Reservation> reservations;
    private final AtomicLong index;

    public MemoryReservationRepository(final MemoryReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
        this.reservations = new ConcurrentHashMap<>();
        this.index = new AtomicLong(1);
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        if (reservations.containsKey(id)) {
            return Optional.of(reservations.get(id));
        }
        return Optional.empty();
    }

    @Override
    public long add(final Reservation reservation) {
        final long id = index.getAndIncrement();
        reservation.setId(id);
        reservations.put(id, reservation);
        return id;
    }

    @Override
    public void deleteById(final Long id) {
        reservations.remove(id);
    }
}
