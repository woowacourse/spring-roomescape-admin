package roomescape.repository;

import roomescape.dto.ReservationTimeDto;
import roomescape.repository.entity.ReservationTimeEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private static final Map<Long, ReservationTimeEntity> repository = new HashMap<>();

    private final AtomicLong atomicLong = new AtomicLong();

    @Override
    public ReservationTimeDto add(ReservationTimeDto reservationTimeDto) {
        Long id = atomicLong.incrementAndGet();
        ReservationTimeEntity reservationTimeEntity = new ReservationTimeEntity(id, reservationTimeDto.startAt());
        repository.put(id, reservationTimeEntity);
        return new ReservationTimeDto(reservationTimeEntity);
    }

    @Override
    public ReservationTimeDto findById(Long id) {
        ReservationTimeEntity reservationTimeEntity = repository.get(id);
        return new ReservationTimeDto(reservationTimeEntity);
    }

    @Override
    public List<ReservationTimeDto> findAll() {
        return repository.values()
                .stream()
                .map(ReservationTimeDto::new)
                .toList();
    }

    @Override
    public int remove(Long id) {
        if (repository.containsKey(id)) {
            repository.remove(id);
            return 1;
        }
        return 0;
    }
}
