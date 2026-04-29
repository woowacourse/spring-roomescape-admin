package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
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

@RestController
@RequestMapping("/times")
public class TimeController {
    private final JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody TimeCreateDto timeCreateDto) {
        String time = timeCreateDto.getStartAt();

        String sql = "insert into reservation_time(start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
                    pstmt.setString(1, time);
                    return pstmt;
                },
                keyHolder);

        ReservationTime created = jdbcTemplate.queryForObject("select id, start_at from reservation_time where id = ?",
                (resultSet, rowNum) -> {
                    return new ReservationTime(resultSet.getLong("id"), resultSet.getString("start_at"));
                },
                keyHolder.getKey().longValue());

        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> findAll() {
        String sql = "select id, start_at from reservation_time";

        List<ReservationTime> find = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new ReservationTime(resultSet.getLong("id"), resultSet.getString("start_at"));
                }
        );

        return ResponseEntity.ok(find);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
