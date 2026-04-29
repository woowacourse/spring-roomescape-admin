package roomescape.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationsDao implements ReservationsDao {

    private final Map<Long, ReservationEntity> reservationEntities;
    private final AtomicLong index;

    public InMemoryReservationsDao() {
        this.reservationEntities = new HashMap<>();
        this.index =  new AtomicLong(0);
    }

    @Override
    public List<ReservationEntity> getReservations() {
        return reservationEntities.values()
                .stream()
                .toList();
    }

    @Override
    public Long saveReservation(ReservationEntity reservationEntity) {
        long now = index.incrementAndGet();
        reservationEntities.put(now, reservationEntity);
        return now;
    }

    @Override
    public void deleteReservationById(Long id) {
        if (!reservationEntities.containsKey(id)) {
            throw new IllegalArgumentException("해당 예약은 존재하지 않습니다.");
        }

        reservationEntities.remove(id);
    }
}
