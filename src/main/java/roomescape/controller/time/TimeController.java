package roomescape.controller.time;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.time.request.TimeRequest;
import roomescape.controller.time.response.TimeResponse;
import roomescape.model.Time;

@RequestMapping("/times")
@RestController
public class TimeController {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;

    public TimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    ResponseEntity<TimeResponse> save(@RequestBody TimeRequest timeRequest) {
        Time time = timeRequest.toTime();
        Long id = saveAndGetId(time);
        return ResponseEntity.ok(TimeResponse.from(id, time));
    }

    private Long saveAndGetId(final Time time) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("start_at", time.getStartAt());
        return getGenerateId(parameters);
    }

    private Long getGenerateId(final Map<String, Object> parameters) {
        Number number = insertActor.executeAndReturnKey(parameters);
        return number.longValue();
    }
}
