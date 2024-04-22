package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.idgenerator.AutoIncrementIdGenerator;
import roomescape.idgenerator.IdGenerator;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeDao timeDao;
    private final IdGenerator idGenerator = new AutoIncrementIdGenerator();

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAllTimes() {
        List<Time> times = timeDao.findAll();
        return ResponseEntity.ok(times.stream()
                .map(TimeResponse::new)
                .toList());
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest timeRequest) {
        Time time = timeRequest.toDomain(idGenerator.generateNewId());
        timeDao.save(time);
        return ResponseEntity.ok(new TimeResponse(time));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        boolean isDeleted = timeDao.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
