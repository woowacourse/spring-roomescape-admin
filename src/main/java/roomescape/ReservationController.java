package roomescape;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationDao.selectReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationDao.insertReservation(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationDao.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
