package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.model.TimeSlot;

public interface TimeSlotRepository {

    Optional<TimeSlot> findById(long id);

    long save(CreateTimeSlotRequest request);

    boolean removeById(long id);

    List<TimeSlot> getTimeSlots();
}
