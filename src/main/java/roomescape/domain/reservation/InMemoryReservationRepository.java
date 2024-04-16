package roomescape.domain.reservation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations = new HashMap<>();
    private AtomicLong index = new AtomicLong(1L);

    @Override
    public Reservation save(Reservation reservation) {
        reservation.setId(index.getAndIncrement());
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream()
                .sorted(Comparator.comparing(Reservation::getId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
