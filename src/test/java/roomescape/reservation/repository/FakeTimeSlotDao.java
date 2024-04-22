package roomescape.reservation.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.reservation.domain.TimeSlot;

public class FakeTimeSlotDao implements TimeSlotRepository {
    private final Map<Long, TimeSlot> timeSlots = new HashMap<>();

    @Override
    public TimeSlot save(final TimeSlot timeSlot) {
        timeSlots.put((long) timeSlots.size() + 1, timeSlot);
        return new TimeSlot((long) timeSlots.size(), timeSlot.getStartAt());
    }

    @Override
    public List<TimeSlot> findAll() {
        return timeSlots.values()
                .stream()
                .toList();
    }
}
