package roomescape.persistence.repository.reservationtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;
import roomescape.exception.InvalidReservationTimeException;

@Repository
public class ReservationTimeMemoryRepository implements ReservationTimeRepository {

    private final AtomicLong id = new AtomicLong(0);
    private final List<ReservationTime> reservationTimes = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Long addAndGetId(ReservationTime requestReservationTime) {
        long newId = id.getAndIncrement();
        ReservationTime reservationTime = new ReservationTime(newId, requestReservationTime.getStartAt());
        reservationTimes.add(reservationTime);
        return newId;
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new InvalidReservationTimeException("존재하지 않는 예약 시간입니다."));
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public void deleteById(Long id) {
        reservationTimes.remove(findById(id));
    }
}
