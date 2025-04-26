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

    private static final int START_INDEX = 1;

    private final ConcurrentHashMap<Long, Reservation> reservations;
    private final AtomicLong index;

    public MemoryReservationRepository() {
        this.reservations = new ConcurrentHashMap<>();
        this.index = new AtomicLong(START_INDEX);
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
    public Reservation add(final Reservation reservation) {
        final long id = index.getAndIncrement();
        final Reservation savedReservation = new Reservation(id, reservation.getName(), reservation.getDate(),
                reservation.getTime());
        reservations.put(id, savedReservation);
        return savedReservation;
    }

    @Override
    public void deleteById(final Long id) {
        reservations.remove(id);
    }
}
