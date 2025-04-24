package roomescape.reservationtime.unit.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.RowMapper;
import roomescape.reservationtime.domain.ReservationTime;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public RowMapper<ReservationTime> createRowMapper() {
        return null;
    }

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(reservationTimes);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.getId().equals(id))
                .findFirst();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        if (reservationTime.getId() == null) {
            reservationTime = new ReservationTime(
                    index.getAndIncrement(),
                    reservationTime.getStartAt()
            );
        }
        reservationTimes.add(reservationTime);
        return reservationTime;
    }

    @Override
    public void deleteById(Long id) {
        reservationTimes.removeIf(reservationTime -> reservationTime.getId().equals(id));
    }
}
