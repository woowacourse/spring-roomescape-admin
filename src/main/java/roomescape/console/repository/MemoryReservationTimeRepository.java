package roomescape.console.repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.persist.entity.ReservationTimeEntity;
import roomescape.persist.repository.ReservationTimeRepository;

public final class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTimeEntity> reservationTimes = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.stream()
                .map(reservationTimeEntity -> new ReservationTime(
                        reservationTimeEntity.getId(),
                        LocalTime.parse(reservationTimeEntity.getStartAt())))
                .toList();
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return reservationTimes.stream()
                .filter(reservationTimeEntity -> reservationTimeEntity.getId() == id)
                .findFirst()
                .map(reservationTimeEntity -> new ReservationTime(
                        reservationTimeEntity.getId(),
                        LocalTime.parse(reservationTimeEntity.getStartAt())));
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        long id = idGenerator.getAndIncrement();
        ReservationTimeEntity reservationTimeEntity = new ReservationTimeEntity(
                id,
                reservationTime.getStartTime().toString()
        );
        reservationTimes.add(reservationTimeEntity);
        return new ReservationTime(
                id,
                reservationTime.getStartTime()
        );
    }

    @Override
    public void removeById(long id) {
        reservationTimes.removeIf(reservationTimeEntity -> reservationTimeEntity.getId() == id);
    }

    @Override
    public boolean existsByStartTime(LocalTime localTime) {
        return reservationTimes.stream()
                .anyMatch(reservationTimeEntity ->
                        LocalTime.parse(reservationTimeEntity.getStartAt()).equals(localTime));
    }
}
