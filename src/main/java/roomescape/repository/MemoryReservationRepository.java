package roomescape.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class MemoryReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public MemoryReservationRepository() {
        this.reservations = new HashMap<>();
        this.index = new AtomicLong(1);
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = index.getAndIncrement();
        reservations.put(id, new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime()));
        return reservations.get(id);
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public void delete(long id) {
        reservations.remove(id);
    }
}
