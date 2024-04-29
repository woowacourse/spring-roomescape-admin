package roomescape.repository;

import roomescape.dto.ReservationRepositoryDto;
import roomescape.repository.entity.ReservationEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private static final Map<Long, ReservationEntity> repository = new HashMap<>();

    private final AtomicLong atomicLong = new AtomicLong();

    @Override
    public ReservationRepositoryDto add(ReservationRepositoryDto reservationRepositoryDto) {
        Long id = atomicLong.incrementAndGet();
        ReservationRepositoryDto repositoryDto = new ReservationRepositoryDto(id, reservationRepositoryDto.name(), reservationRepositoryDto.date(), reservationRepositoryDto.timeId());
        ReservationEntity reservationEntity = repositoryDto.toEntity();
        repository.put(id, reservationEntity);
        return repositoryDto;
    }

    @Override
    public List<ReservationRepositoryDto> findAll() {
        return repository.values()
                .stream()
                .map(ReservationRepositoryDto::new)
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
