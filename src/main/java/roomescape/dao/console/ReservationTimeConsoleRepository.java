package roomescape.dao.console;

import roomescape.dao.ReservationTimeRepository;
import roomescape.entity.ReservationTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationTimeConsoleRepository implements ReservationTimeRepository {

    private final AtomicLong idIndex = new AtomicLong(1);


    private final ConcurrentMap<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        Long id = idIndex.getAndIncrement();
        ReservationTime createdReservationTime = new ReservationTime(id, reservationTime.getStartAt());
        reservationTimes.put(id, createdReservationTime);
        return createdReservationTime;
    }

    @Override
    public ReservationTime findReservationTime(Long id) {
        ReservationTime reservationTime = reservationTimes.get(id);
        if (reservationTime == null) {
            throw new IllegalArgumentException("해당 id 데이터가 존재하지 않습니다");
        }
        return reservationTimes.get(id);
    }

    @Override
    public List<ReservationTime> findReservationTimes() {
        return new ArrayList<>(reservationTimes.values());
    }

    @Override
    public void removeReservationTime(Long id) {
        reservationTimes.remove(id);
    }
}
