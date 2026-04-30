package roomescape.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationCommandService;
import roomescape.service.ReservationQueryService;
import roomescape.service.ReservationTimeCommandService;
import roomescape.service.ReservationTimeQueryService;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;
    private final ReservationTimeCommandService reservationTimeCommandService;
    private final ReservationTimeQueryService reservationTimeQueryService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationQueryService.getAllReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse reservationResponse = reservationCommandService.create(request.name(), request.date(),
                request.timeId());
        return ResponseEntity.status(HttpStatus.OK).body(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationCommandService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponse>> getAllTimes() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationTimeQueryService.findAllReservationTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponse> createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTimeResponse reservationTimeResponse = reservationTimeCommandService.create(request.startAt());
        return ResponseEntity.status(HttpStatus.OK).body(reservationTimeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeCommandService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
