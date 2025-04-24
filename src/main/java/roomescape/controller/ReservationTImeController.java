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
import roomescape.repository.ReservationTImeRepository;
import roomescape.service.request.CreateReservationTimeRequest;
import roomescape.service.response.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class ReservationTImeController {

    private final ReservationTImeRepository reservationTImeRepository;

    public ReservationTImeController(ReservationTImeRepository reservationTImeRepository) {
        this.reservationTImeRepository = reservationTImeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody CreateReservationTimeRequest createReservationTImeRequest) {
        Long id = reservationTImeRepository.create(new ReservationTime(createReservationTImeRequest.startAt()));
        ReservationTime reservationTime = reservationTImeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.ok(new ReservationTimeResponse(
                reservationTime.id(),
                reservationTime.startAt()
        ));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTime> reservationTimes = reservationTImeRepository.findAll();
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimes.stream()
                .map(reservationTime -> new ReservationTimeResponse(
                        reservationTime.id(),
                        reservationTime.startAt()
                )).toList();
        return ResponseEntity.ok(reservationTimeResponses);
    }

    @DeleteMapping("/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("reservationTimeId") Long reservationTimeId) {
        reservationTImeRepository.deleteById(reservationTimeId);
        return ResponseEntity.ok().build();
    }
}
