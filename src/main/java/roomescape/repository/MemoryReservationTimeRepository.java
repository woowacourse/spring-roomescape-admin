package roomescape.repository;

import roomescape.dto.ReservationRepositoryTimeDto;
import roomescape.repository.entity.ReservationTimeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private static final Map<Long, ReservationTimeEntity> repository = new HashMap<>();

    private final AtomicLong atomicLong = new AtomicLong();

    @Override
    public ReservationRepositoryTimeDto add(ReservationRepositoryTimeDto reservationRepositoryTimeDto) {
        Long id = atomicLong.incrementAndGet();
        ReservationTimeEntity reservationTimeEntity = new ReservationTimeEntity(id, reservationRepositoryTimeDto.startAt());
        repository.put(id, reservationTimeEntity);
        return new ReservationRepositoryTimeDto(reservationTimeEntity);
    }

    @Override
    public ReservationRepositoryTimeDto findById(Long id) {
        ReservationTimeEntity reservationTimeEntity = repository.get(id);
        return new ReservationRepositoryTimeDto(reservationTimeEntity);
    }

    @Override
    public List<ReservationRepositoryTimeDto> findAll() {
        return repository.values()
                .stream()
                .map(ReservationRepositoryTimeDto::new)
                .toList();
    }

    @Override
    public boolean remove(Long id) {
        if (repository.containsKey(id)) {
            repository.remove(id);
            return true;
        }
        return false;
    }
}
