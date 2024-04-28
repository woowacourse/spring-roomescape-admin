package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private static final Map<Long, ReservationTime> repository = new HashMap<>();

    private final AtomicLong atomicLong = new AtomicLong();

    @Override
    public ReservationTimeDto add(ReservationTimeDto reservationTimeDto) {
        Long id = atomicLong.incrementAndGet();
        repository.put(id, new ReservationTime(id, reservationTimeDto.startAt()));
        return new ReservationTimeDto(id, reservationTimeDto.startAt());
    }

    @Override
    public ReservationTimeDto findById(Long id) {
        ReservationTime reservationTime = repository.get(id);
        return new ReservationTimeDto(reservationTime.getId(), reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTimeDto> findAll() {
        return repository.values()
                .stream()
                .map(reservationTime -> new ReservationTimeDto(reservationTime.getId(), reservationTime.getStartAt()))
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
