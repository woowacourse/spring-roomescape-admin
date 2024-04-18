package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    private final Map<Long, Reservation> reservations;

    private AtomicLong index;

    public MemoryReservationRepository() {
        this.reservations = new HashMap<>();
        this.index = new AtomicLong(0);
    }

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
        if (!reservations.containsKey(reservationId)) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }

        reservations.remove(reservationId);
    }
}
