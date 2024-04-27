package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.dao.TimeManagementDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeManagementController {

    private final TimeManagementDao timeManagementDao;

    public TimeManagementController(TimeManagementDao timeManagementDao) {
        this.timeManagementDao = timeManagementDao;
    }

    @GetMapping
    public List<Time> read() {
        return timeManagementDao.findAll();
    }

    @PostMapping
    public Time create(@RequestBody TimeRequest timeRequest) {
        return timeManagementDao.insert(timeRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        timeManagementDao.delete(id);
    }
}
