package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.error.ReferDataDeleteException;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeResponse;
import roomescape.time.dto.TimeSaveRequest;
import roomescape.time.repository.TimeRepository;

@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public TimeResponse save(final TimeSaveRequest timeSaveRequest) {
        Time time = timeRepository.save(timeSaveRequest.toTime());
        return TimeResponse.toResponse(time);
    }

    public TimeResponse findById(final Long id) {
        Time time = timeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 시간이 없습니다."));
        return TimeResponse.toResponse(time);
    }

    public List<TimeResponse> findAll() {
        List<Time> times = timeRepository.findAll();
        return times.stream()
                .map(TimeResponse::toResponse)
                .toList();
    }

    public void deleteById(final Long id) {
        findById(id);
        if (timeRepository.findBySameReferId(id).isPresent()) {
            throw new ReferDataDeleteException("해당 시간으로 예약한 정보가 존재합니다.");
        }
        timeRepository.deleteById(id);
    }
}
