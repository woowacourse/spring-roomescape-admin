package roomescape.controller;

import java.util.List;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.AddReservationDto;
import roomescape.model.Reservation;
import roomescape.model.Reservations;

@Controller
public class RoomescapeController {
    private final Reservations reservations;
    private AtomicLong index = new AtomicLong(1);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RoomescapeController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservationsFromDB = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
            return reservation;
        });
        return ResponseEntity.ok(reservationsFromDB);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody AddReservationDto addReservationDto) {
        Reservation newReservation = AddReservationDto.toEntity(getIndexAndIncrement(), addReservationDto);
        reservations.addReservation(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    public Long getIndexAndIncrement() {
        return index.getAndIncrement();
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable long id) {
        Reservation oldReservation = reservations.deleteReservation(id);
        return ResponseEntity.ok().body(oldReservation);
    }

}
