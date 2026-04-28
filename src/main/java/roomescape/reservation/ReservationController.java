package roomescape.reservation;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
    );

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", reservationRowMapper);
    }

    @PostMapping("/reservations")
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        Map<String, Object> params = Map.of(
                "name", reservationRequest.name(),
                "date", reservationRequest.date(),
                "time", reservationRequest.time()
        );
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return ReservationResponse.from(reservationRequest.toDomain(id));
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
