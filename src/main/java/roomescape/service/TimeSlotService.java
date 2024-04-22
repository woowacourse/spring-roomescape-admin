package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.TimeSlotCreationRequest;
import roomescape.controller.dto.TimeSlotCreationResponse;
import roomescape.domain.TimeSlot;
import roomescape.repository.TimeSlotRepository;
import roomescape.service.dto.TimeSlotDto;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<TimeSlotCreationResponse> getAllTimes() {
        return timeSlotRepository.findAll()
                .stream()
                .map(TimeSlotCreationResponse::from)
                .toList();
    }

    public TimeSlotCreationResponse addTime(TimeSlotCreationRequest request) {
        TimeSlot timeSlot = request.toEntity();
        TimeSlotDto dto = TimeSlotDto.from(timeSlot);
        validateUniqueTime(dto.getTime());
        TimeSlot newTimeSlot = timeSlotRepository.create(dto);
        return TimeSlotCreationResponse.from(newTimeSlot);
    }

    private void validateUniqueTime(LocalTime time) {
        if (timeSlotRepository.existsByTime(time)) {
            throw new IllegalArgumentException("시간을 중복하여 등록할 수 없습니다.");
        }
    }

    public void removeTime(Long id) {
        timeSlotRepository.deleteById(id);
    }
}
