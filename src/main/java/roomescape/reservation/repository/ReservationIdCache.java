package roomescape.reservation.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;

@Component
public class ReservationIdCache {
    private final Map<Reservation, Long> cache = new ConcurrentHashMap<>();

    public void put(Reservation reservation, Long id) {
        cache.put(reservation, id);
    }

    public long get(Reservation reservation) {
        return cache.get(reservation);
    }

    public void clear() {
        cache.clear();
    }
}
