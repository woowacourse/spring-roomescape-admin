package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationTimeController {

    private ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        return ResponseEntity.ok().body(reservationTimeDao.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        Long id = reservationTimeDao.insert(reservationTime);
        return ResponseEntity.ok().body(reservationTimeDao.findBy(id));
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
