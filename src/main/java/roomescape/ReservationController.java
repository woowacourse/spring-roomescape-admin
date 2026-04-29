package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationController {

    private ReservationDao reservationDao;
    private ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        return ResponseEntity.ok().body(reservationDao.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        ReservationTime time = reservationTimeDao.findBy(request.getTimeId());
        Reservation reservation = new Reservation(
                null,
                request.getName(),
                request.getDate(),
                time);
        Long id = reservationDao.insert(reservation);
        return ResponseEntity.ok().body(reservationDao.findBy(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
