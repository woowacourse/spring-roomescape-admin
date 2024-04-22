package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> rowMapper;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    }

    @GetMapping
    public ResponseEntity<List<Time>> readTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return ResponseEntity.ok(jdbcTemplate.query(sql, rowMapper));
    }
}

