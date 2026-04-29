package roomescape.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {
    private final ConcurrentHashMap<Long, Reservation> reservationMap = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    public Reservation findById(long id) {
        return reservationMap.get(id);
    }
    public long save(Reservation reservation) {
        if (reservation.getId() == null || !reservationMap.containsKey(reservation.getId())) {
            final long newId = index.getAndIncrement();
            reservationMap.put(newId, new Reservation(newId, reservation.getReservationName(), reservation.getReservationDateTime()));
            return newId;
        }

        reservationMap.put(reservation.getId(), reservation);
        return reservation.getId();
    }

    public void deleteById(long id) {
        reservationMap.remove(id);
    }

    public List<Reservation> findAll() {
        return reservationMap.values().stream().toList();
    }
}
