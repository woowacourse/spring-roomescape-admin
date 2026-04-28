package roomescape.reservation;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservationTime.ReservationTime;

@RestController
public class ReservationController {
    private static final String SELECT_WITH_JOIN =
            "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value " +
            "FROM reservation AS r " +
            "INNER JOIN reservation_time AS t ON r.time_id = t.id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())
    );

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return jdbcTemplate.query(SELECT_WITH_JOIN, reservationRowMapper);
    }

    @PostMapping("/reservations")
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        Map<String, Object> params = Map.of(
                "name", reservationRequest.name(),
                "date", reservationRequest.date(),
                "time_id", reservationRequest.timeId()
        );
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        Reservation reservation = jdbcTemplate.queryForObject(
                SELECT_WITH_JOIN + " WHERE r.id = ?", reservationRowMapper, id);
        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}