package roomescape.controller;

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
import roomescape.domain.time.ReservationTime;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeDto>> findReservationTimes() {
        List<ReservationTimeDto> times = reservationTimeRepository.findAll().stream()
                .map(ReservationTimeDto::from)
                .toList();
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeDto> createReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime newReservationTime = reservationTimeRepository.save(reservationTimeRequest.toTime());
        return ResponseEntity.created(URI.create("/times/" + newReservationTime.getId()))
                .body(ReservationTimeDto.from(newReservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable(value = "id") Long id) {
        try {
            reservationTimeRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
