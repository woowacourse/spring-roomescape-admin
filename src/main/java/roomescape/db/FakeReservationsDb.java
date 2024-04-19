package roomescape.db;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import roomescape.domain.Reservation;

public class FakeReservationsDb {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public List<Reservation> readAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    public void create(Reservation reservation) {
        final long id = reservation.getId();
        if (reservations.containsKey(id)) {
            throw new IllegalArgumentException("duplicated key exists.");
        }
        reservations.put(id, reservation);
    }

    public boolean delete(long id) {
        boolean exists = reservations.containsKey(id);
        if (exists) {
            reservations.remove(id);
        }
        return exists;
    }
}
