package roomescape.reservation.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.reservation.repository.dto.Reservation;

public class InMemoryReservationsRepository implements ReservationsRepository {

    private final Map<Long, ReservationEntity> reservationEntities;
    private final AtomicLong index;

    public InMemoryReservationsRepository() {
        this.reservationEntities = new HashMap<>();
        this.index =  new AtomicLong(0);
    }

    @Override
    public List<Reservation> findAllReservationsWithTime() {
        return List.of();
    }

    @Override
    public ReservationEntity saveReservation(ReservationEntity entity) {
        long id = index.incrementAndGet();

        ReservationEntity entityWithId = entity.updateId(id);
        reservationEntities.put(id, entityWithId);

        return entityWithId;
    }

    @Override
    public void deleteReservationById(Long id) {
        if (!reservationEntities.containsKey(id)) {
            throw new IllegalArgumentException("해당 예약은 존재하지 않습니다.");
        }

        reservationEntities.remove(id);
    }
}
