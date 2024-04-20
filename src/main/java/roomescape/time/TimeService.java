package roomescape.time;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TimeService {
    private final TimeRepository timeRepository;

    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public Long save(final TimeRequest timeRequest) {
        return timeRepository.save(timeRequest);
    }

    @Transactional(readOnly = true)
    public Time findById(final Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 예약이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public void deleteById(final Long id) {
        findById(id);
        timeRepository.deleteById(id);
    }
}
