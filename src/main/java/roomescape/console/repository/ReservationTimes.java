package roomescape.console.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class ReservationTimes implements ReservationTimeRepository {
    private final AtomicLong index = new AtomicLong(1);
    private final List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime entity = new ReservationTime(index.getAndIncrement(), reservationTime);
        reservationTimes.add(entity);
        return entity;
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public ReservationTime findById(long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다."));
    }

    @Override
    public void deleteById(long id) {
        ReservationTime found = findById(id);
        reservationTimes.remove(found);
    }
}
