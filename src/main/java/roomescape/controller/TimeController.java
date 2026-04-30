package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.persistence.dao.TimeDao;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        Long id = timeDao.insert(time);
        Time timeById = timeDao.findById(id);
        return ResponseEntity.ok(timeById);
    }

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        List<Time> times = timeDao.findAll();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id){
        timeDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
