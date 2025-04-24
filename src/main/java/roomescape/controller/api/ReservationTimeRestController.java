package roomescape.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeGetResponse;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeRestController {

    private final ReservationService reservationService;

    public ReservationTimeRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeGetResponse>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationService.getAllReservationTime();
        List<ReservationTimeGetResponse> reservationTimeGetResponses = reservationTimes.stream()
                .map(ReservationTimeGetResponse::from)
                .toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationTimeGetResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeGetResponse> addReservationTime(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        try {
            reservationService.validateDuplicateStartTime(reservationTimeCreateRequest.startAt());
            ReservationTime newReservationTime = reservationService.addReservationTimeAndReturn(reservationTimeCreateRequest.startAt());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ReservationTimeGetResponse.from(newReservationTime));
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long id) {
        try {
            reservationService.deleteReservationTimeById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
