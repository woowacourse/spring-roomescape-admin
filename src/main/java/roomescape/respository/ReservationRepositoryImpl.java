package roomescape.respository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.reservation.Reservation;

public class ReservationRepositoryImpl implements ReservationRepository {

    public static final int INITIAL_ID_VALUE = 1;
    private final AtomicLong reservationId = new AtomicLong(INITIAL_ID_VALUE);
    private final Map<Long, Reservation> store = new ConcurrentHashMap<>();

    @Override
    public List<Reservation> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        long id = reservationId.getAndIncrement();
        Reservation newReservation = new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime()
        );
        store.put(id, newReservation);
        return newReservation;
    }

    @Override
    public Boolean exist(long id) {
        return store.containsKey(id);
    }

    @Override
    public void delete(long id) {
        store.remove(id);
    }
}
