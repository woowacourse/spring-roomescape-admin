package roomescape.model;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class Reservations {

    private final ConcurrentHashMap<Long, Reservation> reservations;
    private Long index;

    public Reservations() {
        this.reservations = new ConcurrentHashMap<>();
        this.index = 1L;
    }

    public long add(final Reservation reservation) {
        reservations.put(index, reservation);
        return index++;
    }

    public void deleteBy(final Long id) {
        if (!reservations.containsKey(id)) {
            throw new NoSuchElementException("해당하는 id의 예약기록이 없습니다.");
        }
        reservations.remove(id);
    }

    public Map<Long, Reservation> getReservations() {
        return reservations;
    }
}
