package roomescape.domain;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository {

    TimeSlot create(TimeSlot timeSlot);

    List<TimeSlot> findAllOrderByTimeAscending();

    Optional<TimeSlot> findById(Long id);

    void deleteById(Long id);

    boolean existsByTime(LocalTime time);

    void deleteAll();
}
