package roomescape.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;

public class MemoryReservationRepository implements ReservationRepository {

    private static final MemoryReservationRepository INSTANCE
        = new MemoryReservationRepository(new HashMap<>(), new AtomicLong(1));

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    private MemoryReservationRepository(Map<Long, Reservation> reservations, AtomicLong index) {
        this.reservations = reservations;
        this.index = index;
    }

    public static MemoryReservationRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Reservation save(Reservation reservation) {
        long id = index.getAndIncrement();
        Reservation newReservation = new Reservation(
            id, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.put(id, newReservation);
        return newReservation;
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
