package roomescape.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationTime> getReservationTimes() {
        return reservationTimeRepository.findAllReservationTimes();
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createStartTime(
            @RequestBody ReservationTime startTime
    ) {
        ReservationTime newReservationTime = reservationTimeRepository.saveReservationTime(startTime);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(
            @PathVariable Long id
    ) {
        reservationTimeRepository.findAllReservationTimes().stream()
                .filter((it -> Objects.equals(it.getId(), id)))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservationTimeRepository.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
