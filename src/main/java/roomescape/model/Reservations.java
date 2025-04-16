package roomescape.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class Reservations {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation add(Reservation reservationWithoutId) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservationWithoutId);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    public void deleteBy(Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약이 없습니다: " + id);
        }
        reservations.remove(id);
    }

    public List<Reservation> getReservations() {
        return reservations.values().stream().toList();
    }
}
