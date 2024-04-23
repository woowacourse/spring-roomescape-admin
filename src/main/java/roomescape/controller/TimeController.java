package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeCreateRequest;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> rowMapper;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationTime> readTimeById(@PathVariable long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return ResponseEntity.ok(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return ResponseEntity.ok(jdbcTemplate.query(sql, rowMapper));
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody TimeCreateRequest dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        long id = jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, dto.startAt());
            return preparedStatement;
        }, keyHolder);

        return ResponseEntity.ok(dto.createTime(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}

