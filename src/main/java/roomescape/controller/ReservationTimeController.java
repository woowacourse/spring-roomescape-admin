package roomescape.controller;

import java.net.URI;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

@RestController
public class ReservationTimeController {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    public ReservationTimeController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/times")
    public List<Time> times() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new Time(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                });
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest timeRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("start_at", timeRequest.getStartAt().toString());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        Time time = timeRequest.toTime(id);
        return ResponseEntity.created(URI.create("/times/" + id))
                .body(new TimeResponse(time));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
