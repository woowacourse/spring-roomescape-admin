package roomescape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class InMemoryReservationsDao implements ReservationsDao {

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public InMemoryReservationsDao() {
        this.reservations = new HashMap<>();
        this.index =  new AtomicLong(0);
    }

    public List<Reservation> getReservations() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public Long addReservation(Reservation reservation) {
        long now = index.incrementAndGet();
        reservations.put(now, reservation);
        return now;
    }

    @Override
    public void deleteReservationById(Long id) {
        if (!reservations.containsKey(id)) {
            throw new IllegalArgumentException("해당 예약은 존재하지 않습니다.");
        }

        reservations.remove(id);
    }
}
