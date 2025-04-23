package roomescape.controller;

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
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        ReservationTime createdReservationTime = reservationTimeRepository.createReservationTime(reservationTime);
        return ResponseEntity.ok(createdReservationTime);
    }

    @GetMapping
    ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.readReservationTimes();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeRepository.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
