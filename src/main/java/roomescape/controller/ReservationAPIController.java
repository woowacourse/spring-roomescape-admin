package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;

@RestController
@RequestMapping("/reservations")
public class ReservationAPIController {

    private final Reservations reservations = new Reservations();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservationList = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")
                        )));
        return ResponseEntity.ok().body(reservationList);
    }

    @PostMapping
    public ResponseEntity<Void> addReservation(@RequestBody ReservationRequest reservationRequest) {
        String sql = "insert into reservation(name, date, time) values (?,?,?)";
        jdbcTemplate.update(sql,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.removeById(id);
        return ResponseEntity.ok().build();
    }
}
