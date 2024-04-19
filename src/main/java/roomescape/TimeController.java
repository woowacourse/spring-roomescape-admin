package roomescape;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TimeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/admin/time")
    public String adminTime() {
        return "admin/time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeDto>> times() {
        String sql = "SELECT id, start_at FROM reservation_time";
        RowMapper<TimeDto> rowMapper = (resultSet, rowNum) -> new TimeDto(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
        List<TimeDto> body = jdbcTemplate.query(sql, rowMapper);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeDto> addTime(@RequestBody TimeDto timeDto) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(timeDto.getStartAt()));
            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        return ResponseEntity
                .created(URI.create("/time/" + id))
                .body(new TimeDto(id, timeDto.getStartAt()));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
