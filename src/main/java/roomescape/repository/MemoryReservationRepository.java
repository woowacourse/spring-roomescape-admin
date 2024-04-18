package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    private static AtomicLong index = new AtomicLong(0);
    private static final Map<Long, Reservation> reservations = new HashMap<>();

    @Override
    public List<Reservation> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public Reservation save(final Reservation reservation) {
        Long reservationIndex = index.incrementAndGet();
        index = new AtomicLong(reservationIndex);
        Reservation savedReservation = reservation.initializeIndex(reservationIndex);
        reservations.put(reservationIndex, savedReservation);

        return savedReservation;
    }

    @Override
    public void deleteById(final Long reservationId) {
        reservations.remove(reservationId);
    }
}
