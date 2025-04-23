package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final MemoryReservationTimeRepository timeRepository;
    private final ConcurrentHashMap<Long, Reservation> reservations;
    private final AtomicLong index;

    public MemoryReservationRepository(final MemoryReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
        this.reservations = new ConcurrentHashMap<>();
        this.index = new AtomicLong(1);
    }

    @Override
    public List<ReservationResponse> findAll() {
        final List<ReservationResponse> responses = new ArrayList<>();
        for (Entry<Long, Reservation> each : reservations.entrySet()) {
            responses.add(ReservationResponse.from(each.getKey(), each.getValue()));
        }
        return responses;
    }

    @Override
    public Optional<ReservationResponse> findById(final Long id) {
        if (reservations.containsKey(id)) {
            return Optional.of(ReservationResponse.from(id, reservations.get(id)));
        }
        return Optional.empty();
    }

    @Override
    public long add(final ReservationRequest request) {
        final ReservationTimeResponse timeResponse = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("예약 시간이 존재하지 않습니다."));
        final long id = index.getAndIncrement();
        final Reservation reservation = request.toEntity(id, timeResponse);
        reservations.put(id, reservation);
        return id;
    }

    @Override
    public void deleteById(final Long id) {
        reservations.remove(id);
    }
}
