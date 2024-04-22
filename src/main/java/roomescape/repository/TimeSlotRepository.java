package roomescape.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.TimeSlot;
import roomescape.service.dto.TimeSlotDto;

public interface TimeSlotRepository {

    TimeSlot create(TimeSlotDto timeSlotDto);

    List<TimeSlot> findAll();

    Optional<TimeSlot> findById(Long id);

    void deleteById(Long id);

    void deleteAll();
}
