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
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> insertTimes(@RequestBody ReservationTime reservationTime) {
        return ResponseEntity.ok(reservationTimeDao.insert(reservationTime));
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> selectTimes() {
        return ResponseEntity.ok(reservationTimeDao.select());
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTimes(@PathVariable long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
