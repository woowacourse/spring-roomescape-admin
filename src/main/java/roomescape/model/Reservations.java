package roomescape.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    private final Map<Long, Reservation> reservations = new HashMap<>();

    public Long add(final Reservation reservation) {
        Long id = ID_GENERATOR.getAndIncrement();

        reservations.put(id, reservation);
        return id;
    }

    public void removeById(final Long id) {
        Reservation reservation = findById(id);
        reservations.remove(id, reservation);
    }

    public Reservation findById(final Long id) {
        if (reservations.containsKey(id)) {
            return reservations.get(id);
        }

        throw new IllegalArgumentException("[ERROR] 찾으려는 id 값이 없습니다.");
    }

    public Map<Long, Reservation> getReservations() {
        return Collections.unmodifiableMap(reservations);
    }
}
