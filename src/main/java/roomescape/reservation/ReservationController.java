package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationQueryingDao reservationQueryingDao;
    private final ReservationUpdatingDao reservationUpdatingDao;

    public ReservationController(ReservationQueryingDao reservationQueryingDao, ReservationUpdatingDao reservationUpdatingDao) {
        this.reservationQueryingDao = reservationQueryingDao;
        this.reservationUpdatingDao = reservationUpdatingDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationQueryingDao.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        Long id = reservationUpdatingDao.insert(request);
        return ResponseEntity.ok().body(reservationQueryingDao.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationUpdatingDao.delete(id);
        return ResponseEntity.ok().build();
    }
}

