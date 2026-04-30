package roomescape.reservationTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final TimeQueryingDao timeQueryingDao;
    private final TimeUpdatingDao timeUpdatingDao;

    public ReservationTimeController(TimeQueryingDao timeQueryingDao, TimeUpdatingDao timeUpdatingDao) {
        this.timeQueryingDao = timeQueryingDao;
        this.timeUpdatingDao = timeUpdatingDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = timeQueryingDao.findAll();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        Long id = timeUpdatingDao.insert(reservationTime);
        return ResponseEntity.ok().body(ReservationTime.toEntity(reservationTime, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeUpdatingDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
