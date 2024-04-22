package roomescape.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.TimeSlot;

@Repository
public interface TimeSlotRepository {
    TimeSlot save(TimeSlot timeSlot);
}
