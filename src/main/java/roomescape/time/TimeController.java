package roomescape.time;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.dto.TimeResponseDto;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Time>> getTimes() {
        String sql = "select * from reservation_time";

        List<Time> times = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                )
        );

        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<TimeResponseDto> createTime(@RequestBody TimeRequestDto request) {
        String sql = "insert into reservation_time(start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.startAt());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Time newTime = new Time(id, request.startAt());

        return ResponseEntity.ok(TimeResponseDto.from(newTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
