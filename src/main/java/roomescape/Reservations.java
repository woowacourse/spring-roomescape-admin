package roomescape;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Reservations {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public void add(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public boolean removeById(long id) {
        Reservation removed = reservations.remove(id);
        return removed != null;
    }

    public List<Reservation> getReservations() {
        return List.copyOf(reservations.values());
    }
}
