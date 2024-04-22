package roomescape.support;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.repository.ReservationTimeRepository;

public class FakeReservationTimeRepository implements ReservationTimeRepository {
    private final Map<Long, ReservationTime> reservationTimes = new HashMap<>();
    private long id = 1;

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        ReservationTime updatedReservationTime = reservationTime.updateId(id++);
        reservationTimes.put(updatedReservationTime.getId(), updatedReservationTime);
        return updatedReservationTime;
    }

    @Override
    public boolean existsByStartAt(LocalTime startAt) {
        return reservationTimes.values().stream()
                .anyMatch(r -> r.getStartAt().equals(startAt));
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        return Optional.ofNullable(reservationTimes.get(id));
    }

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(reservationTimes.values());
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(r -> reservationTimes.remove(id));
    }
}
