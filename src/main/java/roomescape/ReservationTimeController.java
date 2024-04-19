package roomescape;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> listReservationTimes() {
        return ResponseEntity.ok(reservationTimeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime reservationTime) {
        ReservationTime newReservationTime = reservationTimeRepository.create(reservationTime);

        URI location = URI.create("/times/" + newReservationTime.id());
        return ResponseEntity.created(location).body(newReservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> createReservationTime(@PathVariable("id") Long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
