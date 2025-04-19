package roomescape.model;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final ConcurrentHashMap<Long, Reservation> reservations;
    private final AtomicLong index;

    public Reservations() {
        this.reservations = new ConcurrentHashMap<>();
        this.index = new AtomicLong(1);
    }

    public long add(final Reservation reservation) {
        reservations.put(index.getAndIncrement(), reservation);
        return index.get();
    }

    public void deleteById(final Long id) {
        if (reservations.containsKey(id)) {
            reservations.remove(id);
            return;
        }
        throw new NoSuchElementException("해당하는 id의 예약기록이 없습니다.");
    }

    public Map<Long, Reservation> getReservations() {
        return reservations;
    }
}
