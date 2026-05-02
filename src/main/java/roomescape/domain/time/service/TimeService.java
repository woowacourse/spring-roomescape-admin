package roomescape.domain.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.time.domain.Time;
import roomescape.domain.time.dto.request.TimeCreateRequestDTO;
import roomescape.domain.time.dto.response.TimeResponseDTO;
import roomescape.domain.time.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<TimeResponseDTO> getTimes() {
        return timeRepository.findAllTimes()
            .stream()
            .map(this::convertTimeToDTO)
            .toList();
    }

    public TimeResponseDTO saveTime(TimeCreateRequestDTO requestDTO) {
        Time time = new Time(requestDTO.startAt());
        return convertTimeToDTO(timeRepository.save(time));
    }

    public void deleteTimeById(Long id) {
        timeRepository.deleteTimeById(id);
    }

    private TimeResponseDTO convertTimeToDTO(Time time) {
        return new TimeResponseDTO(time.getId(), time.getStartAt());
    }
}
