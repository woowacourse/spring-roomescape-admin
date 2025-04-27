package roomescape.persist.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationDateTimeFormatter;
import roomescape.domain.ReservationTime;
import roomescape.persist.entity.ReservationTimeEntity;

public final class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTimeEntity> reservationTimes = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values().stream()
                .map(ReservationTimeEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return reservationTimes.values().stream()
                .filter(reservationTimeEntity -> reservationTimeEntity.getId() == id)
                .findFirst()
                .map(ReservationTimeEntity::toDomain);
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        long id = idGenerator.getAndIncrement();
        ReservationTimeEntity reservationTimeEntity = ReservationTimeEntity.fromDomain(reservationTime).copyWithId(id);
        reservationTimes.put(id, reservationTimeEntity);
        return reservationTimeEntity.toDomain();
    }

    @Override
    public void removeById(long id) {
        reservationTimes.remove(id);
    }

    @Override
    public boolean existsByStartTime(LocalTime localTime) {
        return reservationTimes.values().stream()
                .anyMatch(reservationTimeEntity ->
                        ReservationDateTimeFormatter.parseTime(reservationTimeEntity.getStartAt()).equals(localTime));
    }
}
