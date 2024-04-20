package roomescape.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationStore {
    private Map<Long, Reservation> store;
    private AtomicLong index = new AtomicLong(0);

    public ReservationStore() {
        this.store = new HashMap<>();
    }

    public void save(Reservation reservation) {
        store.put(index.incrementAndGet(), reservation);
    }

    public List<Reservation> getAll() {
        return store.values().stream().toList();
    }

    public Reservation delete(long id) {
        Reservation findReservation = store.get(id);
        store.remove(id);

        return findReservation;
    }
}
