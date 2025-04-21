package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.ReservationTimeSlot;

public class TimeSlotFakeRepository implements TimeSlotRepository {

    private final Map<Long, ReservationTimeSlot> timeSlots = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public Optional<ReservationTimeSlot> findById(final long id) {
        return Optional.ofNullable(timeSlots.get(id));
    }

    public long save(CreateTimeSlotRequest request) {
        final var timeSlot = request.toEntity(index.getAndIncrement());
        timeSlots.put(timeSlot.id(), timeSlot);
        return timeSlot.id();
    }

    public boolean removeById(long id) {
        ReservationTimeSlot removed = timeSlots.remove(id);
        return removed != null;
    }

    public List<ReservationTimeSlot> getTimeSlots() {
        return List.copyOf(timeSlots.values());
    }
}
