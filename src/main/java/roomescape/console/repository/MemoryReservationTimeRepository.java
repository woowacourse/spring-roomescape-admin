package roomescape.console.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationTimeRepository;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes;
    private final AtomicLong index = new AtomicLong(1);

    public MemoryReservationTimeRepository() {
        this.reservationTimes = new ArrayList<>();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime savedReservationTime = new ReservationTime(index.getAndIncrement(), reservationTime.getStartAt());
        reservationTimes.add(savedReservationTime);

        return savedReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findAny();
    }

    @Override
    public void delete(ReservationTime reservationTime) {
        reservationTimes.remove(reservationTime);
    }

    @Override
    public void deleteAll() {
        reservationTimes.clear();
    }
}
