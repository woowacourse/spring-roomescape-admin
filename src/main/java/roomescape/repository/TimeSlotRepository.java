package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.repository.dto.SaveTimeSlotDto;
import roomescape.model.TimeSlot;

public interface TimeSlotRepository {

    Optional<TimeSlot> findById(long id);

    long save(SaveTimeSlotDto request);

    boolean removeById(long id);

    List<TimeSlot> getTimeSlots();
}
