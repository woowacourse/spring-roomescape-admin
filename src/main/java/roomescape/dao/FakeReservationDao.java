package roomescape.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Component
public class FakeReservationDao implements ReservationDao {
    private final Map<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public long save(ReservationRequest reservationRequest) {
        long id = index.getAndIncrement();
        Reservation reservation = new Reservation(id, reservationRequest);
        if (id == reservation.getId()) {
            reservations.put(id, reservation);
        }
        return id;
    }

    @Override
    public boolean existsById(long id) {
        return reservations.containsKey(id);
    }

    @Override
    public void deleteById(long id) {
        reservations.remove(id);
    }
}
