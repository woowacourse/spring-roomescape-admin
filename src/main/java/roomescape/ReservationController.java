package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Long generatedId = reservationDAO.insertWithKeyHolder(reservation);

        Reservation newReservation = Reservation.toEntity(reservation, generatedId);

        return ResponseEntity.ok().body(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservationDAO.findAllReservation());
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<List<Reservation>> delete(@PathVariable Long id) {
        reservationDAO.delete(id);

        return ResponseEntity.ok().body(reservationDAO.findAllReservation());
    }
}
