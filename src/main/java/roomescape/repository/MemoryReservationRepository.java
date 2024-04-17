package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryReservationRepository implements ReservationRepository {
    private static AtomicLong index = new AtomicLong(0);
    private static final Map<Long, ReservationEntity> reservations = new HashMap<>();

    @Override
    public List<ReservationEntity> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public ReservationEntity save(final ReservationEntity reservation) {
        Long reservationIndex = index.incrementAndGet();
        index = new AtomicLong(reservationIndex);
        ReservationEntity savedReservation = reservation.initializeIndex(reservationIndex);
        reservations.put(reservationIndex, savedReservation);

        return savedReservation;
    }

    @Override
    public void deleteById(final Long reservationId) {
        reservations.remove(reservationId);
    }
}
