package roomescape.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;

@Service
public class ReservationService {

    private Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private AtomicLong index = new AtomicLong(1);

    public Reservation addReservation(Reservation reservation) {
        return reservations.put(reservation.getId(), reservation);
    }

    public Reservation deleteReservation(long id) {
        if(!reservations.containsKey(id)) {
            throw new IllegalArgumentException("해당 ID 없음");
        }
        return reservations.remove(id);
    }

    public List<Reservation> getReservations() {
        return reservations.values().stream()
                .toList();
    }

    public Long getIndexAndIncrement() {
        return index.getAndIncrement();
    }
}
