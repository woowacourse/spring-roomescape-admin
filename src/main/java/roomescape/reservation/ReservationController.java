package roomescape.reservation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.time.ReservationTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, __) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime()
            )
    );

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        return ResponseEntity.ok(jdbcTemplate.query(
                """
                        SELECT
                            r.id AS reservation_id,
                            r.name,
                            r.date,
                            t.id AS time_id,
                            t.start_at AS time_value
                        FROM reservation AS r
                        INNER JOIN reservation_time AS t
                        ON r.time_id = t.id""",
                reservationRowMapper
        ));
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", request.name())
                .addValue("date", request.date())
                .addValue("time_id", request.timeId());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        Reservation reservation = jdbcTemplate.queryForObject(
                "SELECT "
                        + "r.id AS reservation_id,"
                        + "r.name,"
                        + "r.date,"
                        + "t.id AS time_id,"
                        + "t.start_at AS time_value "
                        + "FROM reservation AS r "
                        + "INNER JOIN reservation_time AS t "
                        + "WHERE r.id = ?", reservationRowMapper, id);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        return ResponseEntity.noContent().build();
    }
}
