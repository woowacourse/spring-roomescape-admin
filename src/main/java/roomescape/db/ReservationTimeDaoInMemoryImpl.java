package roomescape.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;


@Primary
@Repository
public class ReservationTimeDaoInMemoryImpl implements ReservationTimeDao {
    private final List<ReservationTime> memory = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        ReservationTime newReservationTime = new ReservationTime(
                index.getAndIncrement(),
                reservationTime.getStartAt()
        );
        memory.add(newReservationTime);
        return newReservationTime;
    }

    @Override
    public List<ReservationTime> findAll() {
        return memory;
    }

    @Override
    public Optional<ReservationTime> findById(final long id) {
        return memory.stream().filter(reservationTime -> reservationTime.getId() == id).findAny();
    }

    @Override
    public void deleteById(final long id) {
        memory.removeIf(reservationTime -> reservationTime.getId() == id);
    }
}
