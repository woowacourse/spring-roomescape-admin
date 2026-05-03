package roomescape.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dao.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private AtomicLong id = new AtomicLong(1);
    private List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public List<ReservationTime> findAll() {
        return reservationTimes;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime savedReservationTime = new ReservationTime(
                id.getAndIncrement(),
                reservationTime.getStartAt()
        );
        reservationTimes.add(savedReservationTime);
        return savedReservationTime;
    }

    @Override
    public void deleteById(Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId().equals(id));
    }
}
