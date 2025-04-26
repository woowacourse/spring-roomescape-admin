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
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationGetResponse;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationGetResponse>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservation();
        List<ReservationGetResponse> reservationGetResponses = reservations.stream()
                .map(ReservationGetResponse::from)
                .toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationGetResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationGetResponse> addReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        try {
            Reservation reservation = reservationService.reserveNewTime(reservationCreateRequest);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ReservationGetResponse.from(reservation));
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationService.deleteReservationById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
