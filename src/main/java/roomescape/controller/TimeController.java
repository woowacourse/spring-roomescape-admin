package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("times")
public class TimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> times() {
        return ResponseEntity.ok(reservationTimeRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTime> times(@RequestBody @Valid ReservationTime reservationTime) {
        return ResponseEntity.ok(reservationTimeRepository.save(reservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> times(@PathVariable Long id) {
        reservationTimeRepository.remove(id);
        return ResponseEntity.noContent().build();
    }
}
