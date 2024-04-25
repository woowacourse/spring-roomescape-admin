package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.TimeSlot;
import roomescape.domain.TimeSlotDto;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeSlot> findAll() {
        return timeRepository.findAll();
    }

    public TimeSlot findById(Long id) {
        return timeRepository.findById(id);
    }

    public TimeSlot create(TimeSlotDto timeSlotDto) {
        Long id = timeRepository.create(timeSlotDto);
        return timeSlotDto.toEntity(id);
    }

    public void delete(Long id) {
        timeRepository.delete(id);
    }
}
