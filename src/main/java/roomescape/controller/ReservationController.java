package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private final ReservationDao reservationDao;

    public @Autowired ReservationController(JdbcTemplate jdbcTemplate) {
        this.reservationDao = new ReservationDao(jdbcTemplate);
    }

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationDao.findAll();
    }

    @ResponseBody
    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        long id = reservationDao.insert(reservationRequestDto);
        return getReservationById(id);
    }

    private Reservation getReservationById(Long id) {
        return reservationDao.findById(id);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
