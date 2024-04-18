package roomescape.domain;

import java.util.*;

public class ReservationStore {
    private Map<Long, Reservation> store;

    public ReservationStore() {
        this.store = new HashMap<>();
    }

    public void save(Reservation reservation) {
        store.put(reservation.getId(), reservation);
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
