package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.dao.TimeDao;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.entity.ReservationTime;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final TimeDao timeDao;

    public ReservationTimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readAll() {
        return ResponseEntity.ok(timeDao.findAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody TimeRequestDto requestDto) {
        return ResponseEntity.ok(timeDao.insert(requestDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        timeDao.delete(id);
    }
}
