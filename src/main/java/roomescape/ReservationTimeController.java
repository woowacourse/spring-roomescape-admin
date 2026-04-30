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

    private final ReservationTimeRepository reservationTimeRepository;


    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        return ResponseEntity.ok(reservationTimeRepository.findAll());
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<ReservationTime> getReservationTime(@PathVariable Long id) {
        ReservationTime reservationTime = reservationTimeRepository.findById(id);
        return ResponseEntity.ok(reservationTime);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = new ReservationTime(request.getStartAt());

        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        return ResponseEntity.ok(savedReservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
