package roomescape.controller;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeCreateResponse;
import roomescape.controller.dto.ReservationTimeFindResponse;
import roomescape.domain.ReservationTime;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeFindResponse>> getReservationTimeRepository() {
        List<ReservationTimeFindResponse> reservationTimeResponses = reservationTimeRepository.findReservationTimes()
                .stream()
                .map(ReservationTimeFindResponse::of)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeCreateResponse> createReservationTime(
            @RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        ReservationTime requestedReservationTime = reservationTimeCreateRequest.toReservationTime();
        ReservationTime createdReservationTime =
                reservationTimeRepository.createReservationTime(requestedReservationTime);

        return ResponseEntity.ok(
                ReservationTimeCreateResponse.of(createdReservationTime)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        try {
            reservationTimeRepository.deleteReservationTimeById(id);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
