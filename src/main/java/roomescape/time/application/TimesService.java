package roomescape.time.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.time.application.dto.TimeInfo;
import roomescape.time.application.dto.TimeRequest;
import roomescape.time.repository.TimeEntity;
import roomescape.time.repository.TimesRepository;

@Service
public class TimesService {

    private final TimesRepository timesRepository;

    @Autowired
    public TimesService(TimesRepository timesRepository) {
        this.timesRepository = timesRepository;
    }

    @Transactional
    public TimeInfo register(TimeRequest request) {
        TimeEntity entity = TimeEntity.of(request.startAt());
        TimeEntity entityWithId = timesRepository.saveTime(entity);

        return TimeInfo.from(entityWithId);
    }

    public List<TimeInfo> getTimes() {
        List<TimeEntity> timeEntities = timesRepository.getTimes();

        return timeEntities.stream()
                .map(TimeInfo::from)
                .toList();
    }

    @Transactional
    public void deleteTimeById(Long id) {
        timesRepository.deleteTimeById(id);
    }
}
