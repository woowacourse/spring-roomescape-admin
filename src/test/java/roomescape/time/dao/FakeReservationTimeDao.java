package roomescape.time.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.time.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final List<ReservationTime> reservationTimes = new CopyOnWriteArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public ReservationTime insert(ReservationTime reservationTime) {
        ReservationTime savedReservationTime = new ReservationTime(index.getAndIncrement(), reservationTime);
        reservationTimes.add(savedReservationTime);
        return savedReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(reservationTimes);
    }

    @Override
    public void delete(long id) {
        ReservationTime targetReservationTime = reservationTimes.stream()
                .filter(time -> Objects.equals(time.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("데이터베이스에 해당 id가 존재하지 않습니다."));

        reservationTimes.remove(targetReservationTime);
    }
}
