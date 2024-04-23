package roomescape.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;
import roomescape.repository.ReservationTimeDao;

public class FakeReservationTimeDao implements ReservationTimeDao {

    Map<Long, ReservationTime> reservationTimes;
    AtomicLong atomicLong = new AtomicLong(0);

    FakeReservationTimeDao() {
        this.reservationTimes = new HashMap<>();
    }

    FakeReservationTimeDao(List<ReservationTime> reservations) {
        this.reservationTimes = new HashMap<>();
        for (int i = 0; i < reservations.size(); i++) {
            this.reservationTimes.put(reservations.get(i).getId(), reservationTimes.get(i));
        }
    }

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes.values().stream().toList();
    }

    @Override
    public ReservationTime findById(Long id) {
        return null;
    }

    @Override
    public ReservationTime insert(ReservationTimeAddRequest reservationTimeAddRequest) {
        Long id = atomicLong.incrementAndGet();
        ReservationTime reservationTime = new ReservationTime(id, reservationTimeAddRequest.getStartAt());
        reservationTimes.put(id, reservationTime);
        return reservationTime;
    }

    @Override
    public int deleteById(Long id) {
        if (reservationTimes.containsKey(id)) {
            reservationTimes.remove(id);
            return 1;
        }
        return 0;
    }
}
