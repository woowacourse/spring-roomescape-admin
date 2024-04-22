package roomescape.controller;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
public class ReservationController {

    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations() {
        String sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id\n";
        String sql1 = "SELECT start_at FROM reservation_time WHERE id = ?";
        List<ReservationResponse> reservations = jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationResponse(rs.getLong("id"), rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(LocalTime.parse(jdbcTemplate.queryForObject(sql1, String.class, rs.getInt("time_id"))))));
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)";
        String sql1 = "SELECT start_at FROM reservation_time WHERE id = ?";
        long id = reservationRequest.timeId();
        int index = jdbcTemplate.update(sql, reservationRequest.name(), reservationRequest.date(), id);
        LocalTime time = LocalTime.parse(jdbcTemplate.queryForObject(sql1, String.class, id));
        return ResponseEntity.created(URI.create("/reservations/" + index)).body(ReservationResponse.of(index, reservationRequest.toReservation(time)));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
