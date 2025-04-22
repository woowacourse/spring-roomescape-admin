package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.ReservationTime;

@Repository
public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final ConcurrentHashMap<Long, ReservationTime> reservationTimes;
    private final AtomicLong index;

    public MemoryReservationTimeRepository() {
        this.reservationTimes = new ConcurrentHashMap<>();
        this.index = new AtomicLong(1);
    }

    @Override
    public List<ReservationTimeResponse> findAll() {
        final ArrayList<ReservationTimeResponse> responses = new ArrayList<>();
        for (Entry<Long, ReservationTime> each : reservationTimes.entrySet()) {
            responses.add(ReservationTimeResponse.from(each.getKey(), each.getValue()));
        }
        return responses;
    }

    @Override
    public Optional<ReservationTimeResponse> findById(final Long id) {
        if (reservationTimes.containsKey(id)) {
            return Optional.of(ReservationTimeResponse.from(id, reservationTimes.get(id)));
        }
        return Optional.empty();
    }

    @Override
    public long add(final ReservationTimeRequest request) {
        final long id = index.getAndIncrement();
        final ReservationTime reservationTime = request.toEntity(id);
        reservationTimes.put(id, reservationTime);
        return id;
    }

    @Override
    public void deleteById(final Long id) {
        reservationTimes.remove(id);
    }
}
