package roomescape.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.data.dto.response.ReservationResponse;
import roomescape.data.vo.Reservation;
import roomescape.data.dto.request.ReservationRequest;
import roomescape.service.ReservationService;

@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.getAllReservationQuery());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody final ReservationRequest reservationRequest) {
        final var savedId = reservationService.createReservationCommand(reservationRequest);

        final var httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/reservations/" + savedId));

        final var response = reservationService.getReservationQuery(savedId);
        return ResponseEntity.status(CREATED)
                .headers(httpHeaders)
                .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") final long id) {
        reservationService.deleteReservationCommand(id);

        return ResponseEntity.status(NO_CONTENT).build();

    }
}
