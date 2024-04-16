package roomescape.domain.reservation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private AtomicLong index = new AtomicLong(1L);
    private final Map<Long, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservation);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream()
                .sorted(Comparator.comparing(Reservation::getId))
                .toList();
    }
}
