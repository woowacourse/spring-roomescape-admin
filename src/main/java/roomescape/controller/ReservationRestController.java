package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeResponse;

@RestController
public class ReservationRestController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRestController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at "
                + "AS time_value FROM reservation AS r INNER JOIN reservation_time AS t ON r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new ReservationResponse(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            new TimeResponse(
                                    resultSet.getLong("time_id"),
                                    LocalTime.parse(resultSet.getString("time_value"))
                            ));
                });
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservationInfo(@RequestBody ReservationRequest reservationRequest) {
        Long timeId = reservationRequest.getTimeId();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationRequest.getName())
                .addValue("date", reservationRequest.getDate().toString())
                .addValue("time_id", timeId);

        LocalTime timeValue = LocalTime.parse(jdbcTemplate.queryForObject(
                "SELECT start_at FROM reservation_time WHERE id = (?)", String.class, timeId));

        Long id = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        Reservation reservation = reservationRequest.toReservation(id, new ReservationTime(timeId, timeValue));
        return ResponseEntity.created(URI.create("/reservations/" + id))
                .body(new ReservationResponse(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationInfo(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
