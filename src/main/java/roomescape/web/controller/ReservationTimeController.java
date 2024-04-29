package roomescape.web.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTimeService;
import roomescape.domain.dto.ReservationTimeRequest;
import roomescape.domain.dto.ReservationTimeResponse;
import roomescape.web.controller.dto.ErrorResponse;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> listReservationTimes() {
        return ResponseEntity.ok(reservationTimeService.getAllReservationTimes());
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse response = reservationTimeService.createReservationTime(request);

        URI location = URI.create("/times/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(IllegalStateException exception) {
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
