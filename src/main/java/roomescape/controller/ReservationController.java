package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(1);
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
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationRequestDto.name(), reservationRequestDto.date(), reservationRequestDto.time());
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Reservation deleteReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(deleteReservation);
        return ResponseEntity.ok().build();
    }
}
