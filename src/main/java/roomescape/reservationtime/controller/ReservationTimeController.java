package roomescape.reservationtime.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.dto.ReservationTimeRequest;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> getTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createTime(@RequestBody @Valid ReservationTimeRequest request) {
        ReservationTime reservationTime = request.toTime();
        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
