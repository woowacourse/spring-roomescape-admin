package roomescape.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Reservations {

    private final Map<Long, Reservation> reservations;
    private final Counter counter;

    public Reservations(final Map<Long, Reservation> reservations, final Counter counter) {
        this.reservations = reservations;
        this.counter = counter;
    }

    public Reservations() {
        this(new HashMap<>(), new Counter());
    }

    public Reservation addReservation(final String name, final LocalDateTime dateTime) {
        final long id = counter.getAndIncrease();
        final Reservation reservation = new Reservation(id, name, dateTime);
        reservations.put(id, reservation);
        return reservation;
    }

    public void deleteById(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("[ERROR] 해당 id가 없습니다.");
        }
        reservations.remove(id);
    }

    public Map<Long, Reservation> getReservations() {
        return Collections.unmodifiableMap(reservations);
    }
}
