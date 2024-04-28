package roomescape.repository;

import roomescape.dto.ReservationRepositoryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private static final Map<Long, ReservationRepositoryDto> repository = new HashMap<>(); // TODO ReservationRepositoryDto 로 매핑되는게 좀 별로

    private final AtomicLong atomicLong = new AtomicLong();

    @Override
    public ReservationRepositoryDto add(ReservationRepositoryDto reservationRepositoryDto) {
        Long id = atomicLong.incrementAndGet();
        ReservationRepositoryDto repositoryDto = new ReservationRepositoryDto(id, reservationRepositoryDto.name(), reservationRepositoryDto.date(), reservationRepositoryDto.timeId());
        repository.put(id, repositoryDto);
        return repositoryDto;
    }

    @Override
    public List<ReservationRepositoryDto> findAll() {
        return repository.values()
                .stream()
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
