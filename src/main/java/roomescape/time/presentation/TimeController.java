package roomescape.time.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


}
