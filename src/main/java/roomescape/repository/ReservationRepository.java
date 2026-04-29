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

    public Reservation findById(Long id) {
        return reservationMap.get(id);
    }
    public long save(Reservation reservation) {
        reservationMap.computeIfAbsent(reservation.getId(), k -> {
            long newId = index.getAndIncrement();
            return new Reservation(newId, reservation.getReservationName(), reservation.getReservationDateTime());
        });

        return reservation.getId();
    }

    public void deleteById(Long id) {
        reservationMap.remove(id);
    }

    public List<Reservation> findAll() {
        return reservationMap.values().stream().toList();
    }
}
