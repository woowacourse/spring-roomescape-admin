package roomescape.reservation.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.reservation.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeRepository {
    private final Map<Long, ReservationTime> reservationTimes = new HashMap<>();

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        reservationTimes.put((long) reservationTimes.size() + 1, reservationTime);
        return new ReservationTime((long) reservationTimes.size(), reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values()
                .stream()
                .toList();
    }

    @Override
    public boolean delete(final long timeId) {
        if (!reservationTimes.containsKey(timeId)) {
            return false;
        }
        reservationTimes.remove(timeId);
        return true;
    }
}
