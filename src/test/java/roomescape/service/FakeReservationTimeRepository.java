package roomescape.service;

import roomescape.model.EntityId;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(reservationTimes);
    }

    @Override
    public boolean existByStartAt(LocalTime startAt) {
        return reservationTimes.stream()
                .anyMatch(reservationTime -> reservationTime.getStartAt().equals(startAt));
    }

    @Override
    public ReservationTime insert(ReservationTime reservationTime) {
        ReservationTime reservationTimeEntity = new ReservationTime(
                EntityId.generate(index.getAndIncrement()), reservationTime.getStartAt());
        reservationTimes.add(reservationTimeEntity);
        return reservationTimeEntity;
    }

    @Override
    public int deleteById(Long id) {
        ReservationTime deleteReservationTime = findById(id);
        if (deleteReservationTime != null) {
            reservationTimes.remove(deleteReservationTime);
            return 1;
        }
        return 0;
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약시간 id입니다."));
    }
}
