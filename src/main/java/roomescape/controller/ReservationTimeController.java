package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreationRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundException;
import roomescape.service.ReservationTimeService;

@RestController
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeService.getAllReservationTime();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(
            @RequestBody ReservationTimeCreationRequest request
    ) {
        try {
            long savedId = reservationTimeService.saveReservationTime(request);
            ReservationTime savedTime = reservationTimeService.getReservationTimeById(savedId);
            return ResponseEntity.created(URI.create("times/" + savedId)).body(savedTime);
        } catch (BadRequestException exception) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/times/{reservationTimeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("reservationTimeId") Long id) {
        try {
            reservationTimeService.deleteReservationTime(id);
            return ResponseEntity.ok().build();
        } catch (BadRequestException exception) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
