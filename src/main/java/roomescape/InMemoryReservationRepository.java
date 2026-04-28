package roomescape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public Optional<Reservation> findById(long id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.put(id, reservation);
        return reservation;
    }

    @Override
    public void delete(long id) {
        reservations.remove(id);
    }
}
