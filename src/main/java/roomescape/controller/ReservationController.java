package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationDAO;

@Controller
public class ReservationController {

    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationDAO.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Long newReservationId = reservationDAO.save(reservation);
        Reservation createdReservation = reservationDAO.findById(newReservationId);
        return ResponseEntity.ok().body(createdReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Reservation> delete(@PathVariable Long id) {
        reservationDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
