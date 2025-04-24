package roomescape.persist.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.persist.entity.ReservationEntity;

public final class FakeReservationRepository implements ReservationRepository {

    private final Map<Long, ReservationEntity> reservations = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations.values().stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        long id = idGenerator.getAndIncrement();
        ReservationEntity reservationEntity = ReservationEntity.fromDomain(reservation).copyWithId(id);
        reservations.put(id, reservationEntity);
        return reservationEntity.toDomain();
    }

    @Override
    public void removeById(long id) {
        reservations.remove(id);
    }
}
