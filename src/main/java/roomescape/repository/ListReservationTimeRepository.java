package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListReservationTimeRepository implements ReservationTimeRepository {
    private static final AtomicLong idCursor = new AtomicLong(1);
    private static final List<ReservationTime> reservationTimes = new ArrayList<>();

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime reservationTimeWithId = reservationTime.withId(idCursor.getAndIncrement());
        reservationTimes.add(reservationTimeWithId);
        return reservationTimeWithId;
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findAny();
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(reservationTimes);
    }

    @Override
    public void delete(Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId().equals(id));
    }
}
