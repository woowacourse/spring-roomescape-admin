package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class FakeReservationRepository implements ReservationRepository {

    private static final int START_ID_NUMBER = 1;

    private final AtomicLong idGenerator = new AtomicLong(START_ID_NUMBER);

    private final Map<Long, ReservationEntity> reservations = new HashMap<>();

    @Override
    public List<ReservationEntity> findAll() {
        return reservations.values()
                .stream()
                .toList();
    }

    @Override
    public ReservationEntity save(final ReservationEntity reservationEntity) {
        reservationEntity.setId(idGenerator.getAndIncrement());
        reservations.put(reservationEntity.getId(), reservationEntity);
        return reservationEntity;
    }

    @Override
    public void delete(final long id) {
        reservations.remove(id);
    }
}
