package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.time.ReservationTime;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        final String sql =
                "SELECT " +
                        "r.id AS reservation_id, " +
                        "r.name, " +
                        "r.date, " +
                        "t.id AS time_id, " +
                        "t.start_at AS time_value " +
                        "FROM reservation AS r " +
                        "INNER JOIN reservation_time AS t " +
                        "ON r.time_id = t.id";

        final List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime()
                )
        ));

        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest reservationRequest) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservationRequest.name())
                .addValue("date", reservationRequest.date())
                .addValue("time_id", reservationRequest.timeId());

        final long id = jdbcInsert.executeAndReturnKey(params).longValue();

        final Reservation reservation = new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                jdbcTemplate.queryForObject("SELECT * FROM reservation_time WHERE id = ?", (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), reservationRequest.timeId())
        );

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }
}
