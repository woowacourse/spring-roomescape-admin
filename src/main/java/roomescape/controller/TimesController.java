package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
public class TimesController {

    private JdbcTemplate jdbcTemplate;

    public TimesController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> reservations() {
        String sql = "SELECT id, start_at FROM reservation_time";
        List<ReservationTimeResponse> reservations = jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTimeResponse(rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()));
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        String sql = "INSERT INTO reservation_time (start_at) values (?)";
        int index = jdbcTemplate.update(sql, reservationTimeRequest.startAt());
        return ResponseEntity.created(URI.create("/times/" + index)).body(ReservationTimeResponse.of(index, reservationTimeRequest));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
