package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final ReservationDao reservationDao;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationDao = new ReservationDao(jdbcTemplate);
    }

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation-legacy";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationDao.findAll();
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        long insertedRow = reservationDao.insert(reservationRequestDto);
        return ResponseEntity.created(URI.create("/reservations/" + insertedRow)).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
