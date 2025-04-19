package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    public Reservation delete(Long id) {
        return reservations.remove(id);
    }

    public boolean isExist(Long id) {
        return reservations.containsKey(id);
    }

    public List<Reservation> findAll() {
        return reservations.values().stream()
                .toList();
    }
}
