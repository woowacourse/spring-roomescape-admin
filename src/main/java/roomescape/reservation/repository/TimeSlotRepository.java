package roomescape.reservation.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.TimeSlot;

@Repository
public interface TimeSlotRepository {
    TimeSlot save(TimeSlot timeSlot);

    List<TimeSlot> findAll();
}
