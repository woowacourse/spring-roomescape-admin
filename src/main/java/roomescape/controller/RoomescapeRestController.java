package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
public class RoomescapeRestController {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    public RoomescapeRestController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> reservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new ReservationResponse(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    );
                });
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservationInfo(@RequestBody ReservationRequest reservationRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("name", reservationRequest.getName());
        params.put("date", reservationRequest.getDate().toString());
        params.put("time", reservationRequest.getTime().toString());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        Reservation reservation = reservationRequest.toReservation(id);
        return ResponseEntity.created(URI.create("/reservations/" + id))
                .body(new ReservationResponse(reservation, id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationInfo(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
