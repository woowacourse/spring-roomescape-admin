package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.repository.dto.SaveTimeSlotDto;
import roomescape.model.TimeSlot;

public class TimeSlotFakeRepository implements TimeSlotRepository {

    private final Map<Long, TimeSlot> timeSlots = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1L);

    @Override
    public Optional<TimeSlot> findById(final long id) {
        return Optional.ofNullable(timeSlots.get(id));
    }

    public long save(SaveTimeSlotDto request) {
        var timeSlot = new TimeSlot(index.getAndIncrement(), request.startAt());
        timeSlots.put(timeSlot.id(), timeSlot);
        return timeSlot.id();
    }

    public boolean removeById(long id) {
        TimeSlot removed = timeSlots.remove(id);
        return removed != null;
    }

    public List<TimeSlot> getTimeSlots() {
        return List.copyOf(timeSlots.values());
    }
}
