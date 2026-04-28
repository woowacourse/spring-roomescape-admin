package roomescape.time;

import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.TimeRequest;
import roomescape.time.dto.TimeResponse;

@RestController
public class TimeController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping("/times")
    public TimeResponse createTime(@RequestBody TimeRequest timeRequest) {
        Map<String, Object> params = Map.of("start_at", timeRequest.startAt());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return TimeResponse.from(timeRequest.toDomain(id));
    }
}
