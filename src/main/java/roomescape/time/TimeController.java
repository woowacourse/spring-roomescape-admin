package roomescape.time;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            resultSet.getTime("start_at").toLocalTime()
    );

    public TimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<Time>> findAll() {
        return ResponseEntity.ok(jdbcTemplate.query("SELECT * FROM reservation_time", timeRowMapper));
    }

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody final Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        Time createdTime = jdbcTemplate.queryForObject(
                "SELECT * FROM reservation_time WHERE id = ?",
                timeRowMapper,
                id
        );
        return ResponseEntity.ok(createdTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Time> delete(@PathVariable final long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        return ResponseEntity.noContent().build();
    }
}
