package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.TimeSlot;
import roomescape.domain.TimeSlotDto;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<TimeSlot> rowMapper =
            (resultSet, rowNum) -> new TimeSlot(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
            );

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping()
    public ResponseEntity<List<TimeSlot>> read() {
        String sql = "select id, start_at from reservation_time";
        List<TimeSlot> reservations = jdbcTemplate.query(sql, rowMapper);

        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<TimeSlot> create(@RequestBody TimeSlotDto timeSlotDto) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("start_at", timeSlotDto.startAt());
        Long id = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        TimeSlot newTimeSlot = timeSlotDto.toEntity(id);

        return ResponseEntity.ok(newTimeSlot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
