package roomescape.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.time.LocalTime;

@RestController
@RequestMapping("/times")
public class ReservationTimeRestController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeRestController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> addReservationTime(@RequestBody LocalTime startAt) {
        try {
            reservationTimeService.validateDuplicateStartTime(startAt);
            ReservationTime newReservationTime = reservationTimeService.addAndGet(startAt);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(newReservationTime);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
