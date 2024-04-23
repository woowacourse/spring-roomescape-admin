package roomescape.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dao.TimeDAO;
import roomescape.dto.TimeCreateRequestDto;
import roomescape.model.Time;

@RestController
public class TimeController {

    private final TimeDAO timeDAO;

    public TimeController(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> times() {
        return ResponseEntity.ok(timeDAO.findAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody TimeCreateRequestDto timeCreateRequestDto) {
        Time time = timeDAO.insert(timeCreateRequestDto);
        return ResponseEntity.ok(time);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            timeDAO.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return ResponseEntity.ok().build();
    }
}
