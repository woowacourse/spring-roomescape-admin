package roomescape.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@Component
public class InMemoryReservationRepository implements ReservationRepository {

    private final AtomicLong incrementId = new AtomicLong(0);
    private final Map<Long, Reservation> storage = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        long nextId = incrementId.incrementAndGet();
        Reservation saved = new Reservation(nextId, reservation.getCustomerName(), reservation.getReservationTime());
        storage.put(nextId, saved);
        return saved;
    }

    @Override
    public Reservations findAll() {
        return new Reservations(new ArrayList<>(storage.values()));
    }

    @Override
    public void saveAll(Reservations reservations) {
        storage.clear();
        List<Reservation> schedule = reservations.getSchedule();
        for (Reservation reservation : schedule) {
            storage.put(reservation.getReservationId(), reservation);
        }
    }
}
