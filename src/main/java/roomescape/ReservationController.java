package roomescape;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = reservationDao.insert(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("/{reservation-id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservation-id") Long reservationId) {
        reservationDao.delete(reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
