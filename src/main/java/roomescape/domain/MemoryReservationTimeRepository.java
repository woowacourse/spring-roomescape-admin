package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> repository = new ArrayList<>();
    private final AtomicLong autoIncreaseId = new AtomicLong(1L);

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return repository.stream()
                .filter(time -> time.id().equals(id))
                .findAny();
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(repository);
    }

    @Override
    public ReservationTime create(ReservationTime reservationTime) {
        Long newId = autoIncreaseId.getAndIncrement();
        ReservationTime createdTime = new ReservationTime(newId, reservationTime.startAt());
        repository.add(createdTime);
        return createdTime;
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(repository::remove);
    }

}
