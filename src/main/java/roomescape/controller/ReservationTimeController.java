package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(savedReservationTime);
    }

    @GetMapping("/times")
    public List<ReservationTime> read() {
        return reservationTimeRepository.findAll();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
