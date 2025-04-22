package roomescape.time.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import roomescape.time.ReservationTime;
import roomescape.time.ReservationTimeDao;

public class FakeReservationTimeDao implements ReservationTimeDao {
    private List<ReservationTime> reservationTimes = new ArrayList<>();
    private long index = 1L;

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(index++, reservationTime.getStartAt());
        reservationTimes.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public boolean removeById(long id) {
        return reservationTimes.removeIf(reservation -> reservation.getId() == id);
    }
}
