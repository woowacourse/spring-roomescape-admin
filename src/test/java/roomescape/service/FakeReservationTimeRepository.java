package roomescape.service;

import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    AtomicLong index = new AtomicLong(1L);
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
        reservationTimes.remove(findById(id).get());
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
        for (ReservationTime reservationTime : reservationTimes) {
            if (reservationTime.getId().equals(id)) {
                return Optional.of(reservationTime);
            }
        }
        return Optional.empty();
    }
}
