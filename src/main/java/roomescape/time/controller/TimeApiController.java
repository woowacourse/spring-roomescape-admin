package roomescape.time.controller;

import java.sql.Time;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.common.Dao;
import roomescape.time.dto.TimeDto;

@RestController
public class TimeApiController {

    private final Dao<Time> timeDao;

    public TimeApiController(Dao<Time> timeDao) {
        this.timeDao = timeDao;
    }

//    @PostMapping
//    public Time add(@RequestBody TimeDto timeDto) {
//
//    }
}
