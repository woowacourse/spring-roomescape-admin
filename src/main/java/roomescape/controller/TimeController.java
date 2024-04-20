package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.TimeDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

@Controller
public class TimeController {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final TimeDao timeDao;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeDao = new TimeDao(jdbcTemplate);
    }

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> insertTime(@RequestBody TimeRequestDto timeRequestDto) {
        long timeId = timeDao.insert(timeRequestDto);
        return ResponseEntity.ok().body(new Time(timeId, timeRequestDto.start_at()));
    }
}
