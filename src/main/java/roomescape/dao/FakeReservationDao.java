package roomescape.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

@Component
public class FakeReservationDao implements ReservationDao {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public void save(Reservation reservation) {
        final long id = reservation.getId();
        if (reservations.containsKey(id)) {
            throw new IllegalArgumentException("duplicated key exists.");
        }
        reservations.put(id, reservation);
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = reservations.containsKey(id);
        if (exists) {
            reservations.remove(id);
        }
        return exists;
    }
}
