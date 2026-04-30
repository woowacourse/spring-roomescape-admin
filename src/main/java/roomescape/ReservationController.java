package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        ReservationTime time = new ReservationTime(
                rs.getLong("time_id"),
                rs.getString("start_at")
        );
        return new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                time
        );
    };

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Reservation> read() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                INNER JOIN reservation_time as t
                  ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setLong(3, request.timeId());
            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        String timeSql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        ReservationTime time = jdbcTemplate.queryForObject(timeSql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        ), request.timeId());
        Reservation newReservation = request.toEntity(id, time);

        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
