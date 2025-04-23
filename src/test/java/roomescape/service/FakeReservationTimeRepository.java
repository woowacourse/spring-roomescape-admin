package roomescape.service;

import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    AtomicLong index = new AtomicLong();
    List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public Long add(final ReservationTime reservationTime) {
        long id = index.getAndIncrement();
        reservationTimes.add(new ReservationTime(id, reservationTime.getStartAt()));
        return id;
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public void deleteById(final Long id) {
        ReservationTime reservationTime = findById(id);
        reservationTimes.remove(reservationTime);
    }

    @Override
    public ReservationTime findById(final Long id) {
        return reservationTimes.stream()
                .filter(reservationTimes -> reservationTimes.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
    }
}
