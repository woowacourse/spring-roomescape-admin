package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationsResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
        ReservationTime tempTime = new ReservationTime(request.getTimeId(), null);
        Reservation reservation = new Reservation(request.getName(), request.getDate(), tempTime);
        ReservationResponse response = reservationService.create(reservation);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ReservationsResponse> read() {
        ReservationsResponse responses = reservationService.findAll();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        int deleteCount = reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
