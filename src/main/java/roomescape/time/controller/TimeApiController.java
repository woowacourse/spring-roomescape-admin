package roomescape.time.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.common.Dao;
import roomescape.time.Time;
import roomescape.time.dto.TimeDto;

@RestController
@RequestMapping("/times")
public class TimeApiController {

    private final Dao<Time> timeDao;

    public TimeApiController(Dao<Time> timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<Time> add(@RequestBody TimeDto timeDto) {
        Time time = timeDto.createTime();
        return ResponseEntity.ok(timeDao.add(time));
    }

    @GetMapping
    public ResponseEntity<List<Time>> getAll() {
        return ResponseEntity.ok(timeDao.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
