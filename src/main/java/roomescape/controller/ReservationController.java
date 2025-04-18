package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.RoomescapeService;

@RestController
public class ReservationController {

    private final RoomescapeService roomescapeService;

    public ReservationController(final RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservationList() {
        List<Reservation> reservations = roomescapeService.findReservations();
        List<ReservationResponse> responses = reservations.stream().map(ReservationResponse::of).toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> reservationAdd(@RequestBody ReservationRequest request) {
        try {
            Reservation savedReservation = roomescapeService.addReservation(request.toReservation());
            return ResponseEntity.ok(ReservationResponse.of(savedReservation));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> reservationRemove(@PathVariable long id) {
        try {
            roomescapeService.removeReservation(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
