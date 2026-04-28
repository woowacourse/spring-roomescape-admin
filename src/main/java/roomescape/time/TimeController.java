package roomescape.time;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

@RestController
public class TimeController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<TimeResponse> timeRowMapper = (rs, rowNum) -> new TimeResponse(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    );

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/times")
    public List<TimeResponse> getTimes() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", timeRowMapper);
    }

    @PostMapping("/times")
    public TimeResponse createTime(@RequestBody TimeRequest timeRequest) {
        Map<String, Object> params = Map.of("start_at", timeRequest.startAt());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return TimeResponse.from(timeRequest.toDomain(id));
    }

    @DeleteMapping("/times/{id}")
    public void deleteTime(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }

}
