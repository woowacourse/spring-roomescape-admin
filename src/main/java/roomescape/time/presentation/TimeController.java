package roomescape.time.presentation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.presentation.dto.TimeInfo;
import roomescape.time.presentation.dto.TimeRequest;
import roomescape.time.repository.TimeEntity;
import roomescape.time.repository.TimesRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimesRepository timesRepository;

    @Autowired
    public TimeController(TimesRepository timesRepository) {
        this.timesRepository = timesRepository;
    }

    @PostMapping
    public ResponseEntity<TimeInfo> createTime(
            @RequestBody TimeRequest request
    ) {
        TimeEntity entity = TimeEntity.of(request.startAt());
        TimeEntity entityWithId = timesRepository.saveTime(entity);

        TimeInfo timeInfo = TimeInfo.from(entityWithId);
        return ResponseEntity.ok(timeInfo);
    }


    @GetMapping
    public ResponseEntity<List<TimeInfo>> getTimes() {
        List<TimeEntity> timeEntities = timesRepository.getTimes();

        List<TimeInfo> timeInfos = timeEntities.stream()
                .map(TimeInfo::from)
                .toList();

        return ResponseEntity.ok(timeInfos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(@PathVariable("id") Long id) {
        timesRepository.deleteTimeById(id);
        return ResponseEntity.ok().build();
    }
}
