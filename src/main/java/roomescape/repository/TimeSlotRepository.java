package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.ReservationTimeSlot;

public interface TimeSlotRepository {

    Optional<ReservationTimeSlot> findById(long id);

    long save(CreateTimeSlotRequest request);

    boolean removeById(long id);

    List<ReservationTimeSlot> getTimeSlots();
}
