package roomescape.dao;

import roomescape.entity.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final Map<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    public FakeReservationTimeDao() {
        insertInitialValues();
    }

    private void insertInitialValues() {
        ReservationTime reservationTime1 = ReservationTime.of(LocalTime.of(10, 0));
        ReservationTime reservationTime2 = ReservationTime.of(LocalTime.of(11, 0));
        insert(reservationTime1);
        insert(reservationTime2);
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values().stream()
                .toList();
    }

    @Override
    public ReservationTime insert(final ReservationTime reservationTime) {
        Long savedId = index.getAndIncrement();
        reservationTimes.put(savedId, reservationTime);
        return reservationTimes.get(savedId);
    }

    @Override
    public boolean deleteById(final Long id) {
        ReservationTime reservationTime = reservationTimes.remove(id);
        return reservationTime != null;
    }
}
