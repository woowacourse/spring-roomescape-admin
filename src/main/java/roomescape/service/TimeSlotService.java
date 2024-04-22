package roomescape.service;

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
        TimeSlot newTimeSlot = timeSlotRepository.create(dto);
        return TimeSlotCreationResponse.from(newTimeSlot);
    }

    public void removeTime(Long id) {
        timeSlotRepository.deleteById(id);
    }
}
