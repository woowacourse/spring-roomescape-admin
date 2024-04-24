package roomescape.time;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class ReservationTimeController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> times() {
        final String sql = "SELECT * FROM reservation_time";
        final List<ReservationTime> times = jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                )
        );

        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("start_at", reservationTimeRequest.startAt());

        final long id = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        final ReservationTime reservationTime = new ReservationTime(id, reservationTimeRequest.startAt());

        return ResponseEntity.ok(reservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
