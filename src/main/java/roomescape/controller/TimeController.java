package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

import java.util.List;

@Controller
public class TimeController {

    @Autowired
    private final TimeDao timeDao;

    public @Autowired TimeController(JdbcTemplate jdbcTemplate) {
        this.timeDao = new TimeDao(jdbcTemplate);
    }

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> insertTime(@RequestBody TimeRequestDto timeRequestDto) {
        long timeId = timeDao.insert(timeRequestDto);
        return ResponseEntity.ok().body(new Time(timeId, timeRequestDto.startAt()));
    }

    @ResponseBody
    @GetMapping("/times")
    public List<Time> getTimes() {
        return timeDao.findAll();
    }
}
