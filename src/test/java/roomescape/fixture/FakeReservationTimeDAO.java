package roomescape.fixture;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dao.ReservationTimeRepository;
import roomescape.model.ReservationTime;

public class FakeReservationTimeDAO implements ReservationTimeRepository {

    private final List<ReservationTime> data = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return data;
    }

    @Override
    public ReservationTime findById(long id) {
        return data.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
    }

    @Override
    public ReservationTime addAndGet(LocalTime startAt) {
        ReservationTime newData = new ReservationTime(atomicLong.getAndIncrement(), startAt);
        data.add(newData);
        return newData;
    }

    @Override
    public int deleteById(Long id) {
        ReservationTime target = data.stream()
                .filter(time -> time.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
        data.remove(target);
        return 1;
    }
}
