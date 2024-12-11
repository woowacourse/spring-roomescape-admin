package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;
import roomescape.service.dto.TimeRequest;
import roomescape.service.dto.TimeResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TimeService {

    private final TimeRepository timeRepository;

    @Transactional
    public TimeResponse create(TimeRequest timeRequest) {
        Time time = timeRequest.toTime();
        Time saved = timeRepository.save(time);

        return new TimeResponse(saved);
    }

    public TimeResponse findOne(long id) {
        Time found = timeRepository.findById(id).orElseThrow();

        return new TimeResponse(found);
    }

    public List<TimeResponse> findAll() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        timeRepository.delete(id);
    }
}
