package roomescape.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes;

    private AtomicLong index = new AtomicLong(0);

    public FakeReservationTimeRepository() {
        this.reservationTimes = new ArrayList<>();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        long currentIndex = index.incrementAndGet();
        ReservationTime reservationTimeEntity = reservationTime.assignId(currentIndex);
        reservationTimes.add(reservationTimeEntity);
        return reservationTimeEntity;
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.hasSameId(id))
                .findAny()
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 ID가 존재하지 않습니다.", 1));
    }

    @Override
    public List<ReservationTime> findAll() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public void deleteById(Long id) {
        Optional<ReservationTime> findReservationTime = reservationTimes.stream()
                .filter(reservationTime -> reservationTime.hasSameId(id))
                .findAny();
        findReservationTime.ifPresent(reservationTimes::remove);
    }
}
