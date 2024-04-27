package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.TimeManagementDao;
import roomescape.domain.Time;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeManagementController {

    private final TimeManagementDao timeManagementDao;

    public TimeManagementController(TimeManagementDao timeManagementDao) {
        this.timeManagementDao = timeManagementDao;
    }

    @GetMapping
    public List<Time> read(){
        return timeManagementDao.findAll();
    }
}
