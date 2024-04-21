package roomescape.controller;

import java.net.URI;
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
import roomescape.domain.time.Time;
import roomescape.dto.TimeDto;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping
    public ResponseEntity<List<TimeDto>> findAll() {
        List<Time> allTimes = timeDao.findAll();
        List<TimeDto> results = allTimes.stream()
                .map(TimeDto::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<TimeDto> create(@RequestBody TimeDto timeDto) {
        Time time = timeDto.toDomain();
        long id = timeDao.add(TimeDto.from(time));
        Time result = timeDao.findById(id);
        return ResponseEntity.created(URI.create("/times/" + id))
                .body(TimeDto.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
