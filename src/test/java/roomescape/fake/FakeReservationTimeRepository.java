package roomescape.fake;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.user.reservationtime.domain.ReservationTime;
import roomescape.user.reservationtime.domain.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> reservationTimes = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Long save(final ReservationTime reservationTime) {
        final Long generatedId = sequence.getAndIncrement();
        final ReservationTime savedReservationTime = new ReservationTime(
                generatedId,
                reservationTime.getStartAt()
        );

        reservationTimes.put(generatedId, savedReservationTime);

        return generatedId;
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
        return Optional.ofNullable(reservationTimes.get(id));
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(reservationTimes.values());
    }

    @Override
    public void deleteById(final Long id) {
        if (!reservationTimes.containsKey(id)) {
            throw new IllegalStateException("ReservationTime with id " + id + " does not exist");
        }

        reservationTimes.remove(id);
    }
}
