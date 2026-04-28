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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(
            @RequestBody ReservationTimeCreateRequest createRequest
    ) {
        ReservationTime createdReservationTime = reservationTimeRepository.create(
                ReservationTime.create(createRequest.startAt()));

        return ResponseEntity.ok(createdReservationTime);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationTime> findById(
            @PathVariable long id
    ) {
        reservationTimeRepository.delete(id);

        return ResponseEntity.ok().build();
    }
}
