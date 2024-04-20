package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeDto;

public class InMemoryReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> database;
    private final AtomicLong idCount;

    public InMemoryReservationTimeRepository() {
        this.database = new HashMap<>();
        idCount = new AtomicLong(1L);
    }

    public InMemoryReservationTimeRepository(Map<Long, ReservationTime> database) {
        this.database = database;
        idCount = new AtomicLong(database.size() + 1);
    }

    @Override
    public ReservationTime create(ReservationTimeDto reservationTimeDto) {
        ReservationTime entity = reservationTimeDto.toEntity(idCount.getAndIncrement());
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<ReservationTime> findAll() {
        return database.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
