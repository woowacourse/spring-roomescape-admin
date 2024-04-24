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
import roomescape.controller.dto.CreateReservationRequest;
import roomescape.controller.dto.CreateReservationResponse;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.service.TimeService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationService reservationService;
    private final TimeService timeService;

    public ReservationController(ReservationService reservationService, TimeService timeService) {
        this.reservationService = reservationService;
        this.timeService = timeService;
    }

    @GetMapping()
    public ResponseEntity<List<CreateReservationResponse>> readAllReservations() {
        List<CreateReservationResponse> reservations = reservationService.readAll()
                .stream()
                .map(CreateReservationResponse::from)
                .toList();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping()
    public ResponseEntity<CreateReservationResponse> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) {
        ReservationTime time = timeService.findById(createReservationRequest.getTimeId());
        Reservation savedReservation = reservationService.saveReservation(createReservationRequest.toEntity(time));
        return ResponseEntity.ok().body(CreateReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
