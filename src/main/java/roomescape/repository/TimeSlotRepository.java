package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import roomescape.domain.TimeSlot;

public interface TimeSlotRepository {

    TimeSlot create(TimeSlot timeSlot);

    List<TimeSlot> findAll();

    Optional<TimeSlot> findById(Long id);

    void deleteById(Long id);

    boolean existsByTime(LocalTime time);

    void deleteAll();
}
