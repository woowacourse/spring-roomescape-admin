package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.model.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {
    private final List<ReservationTime> reservationTimes;
    private final AtomicLong id = new AtomicLong(1L);

    public MemoryReservationTimeRepository() {
        reservationTimes = new ArrayList<ReservationTime>();
    }

    @Override
    public ReservationTime addTime(String start_at) {
        ReservationTime reservationTime = new ReservationTime(id.getAndIncrement(), start_at);
        reservationTimes.add(reservationTime);
        return reservationTime;
    }

    @Override
    public List<ReservationTime> getAllTime() {
        return new ArrayList<>(reservationTimes);
    }

    @Override
    public Integer deleteTime(Long id) {
        for (ReservationTime reservationTime : reservationTimes) {
            if (reservationTime.getId().equals(id)) {
                reservationTimes.remove(reservationTime);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public ReservationTime getReservationTimeById(Long id) {
        for (ReservationTime reservationTime : reservationTimes) {
            if (reservationTime.getId().equals(id)) {
                return reservationTime;
            }
        }
        return null;
    }
}
