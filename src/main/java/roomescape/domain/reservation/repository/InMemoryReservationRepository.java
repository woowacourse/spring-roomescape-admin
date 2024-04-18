package roomescape.domain.reservation.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

@Repository
public class InMemoryReservationRepository implements ReservationRepository {
    private static final long INITIAL_VALUE = 1L;

    private final Map<Long, Reservation> reservations;
    private final AtomicLong index;

    public InMemoryReservationRepository() {
        this.reservations = new ConcurrentSkipListMap<>();
        this.index = new AtomicLong(INITIAL_VALUE);
    }

    @Override
    public Reservation save(Reservation reservationRequest) {
        Reservation newReservation = new Reservation(index.getAndIncrement(), reservationRequest);
        reservations.put(newReservation.getId(), newReservation);
        return newReservation;
    }

    @Override
    public List<Reservation> findAll() {
        return List.copyOf(reservations.values());
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(id);
    }
}
