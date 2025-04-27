package roomescape.controller;

import java.util.List;
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
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeService reservationTimeService;

    public TimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTime> getReservationTimes() {
        return reservationTimeService.findAllReservationTime();
    }

    @PostMapping
    public ResponseEntity<ReservationTime> createStartTime(
            @RequestBody ReservationTime startTime
    ) {
        validateReservationTimeAvailability(startTime);
        ReservationTime newReservationTime = reservationTimeService.savaReservationTime(startTime);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(
            @PathVariable Long id
    ) {
        validateDeleteReservationTimeAvailability(id);

        reservationTimeService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    private void validateDeleteReservationTimeAvailability(Long id) {
        try {
            reservationTimeService.validateDeleteReservationTimeAvailability(id);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void validateReservationTimeAvailability(ReservationTime startTime) {
        try {
            reservationTimeService.validateSaveReservationTimeAvailability(startTime);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
