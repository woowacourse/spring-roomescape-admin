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
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.model.ReservationWithTimeId;
import roomescape.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> reservations() {

        List<Reservation> reservations = reservationService.selectAllReservation();

        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::toDto).toList();

        return ResponseEntity.ok().body(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {

        ReservationWithTimeId reservationWithTimeId = request.toReservationWithId();

        Reservation addedReservation = reservationService.addReservation(reservationWithTimeId);

        ReservationResponse reservationResponse = ReservationResponse.toDto(addedReservation);

        return ResponseEntity.ok().body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {

        int effectedRow = reservationService.deleteReservationById(id);

        if (effectedRow == 1) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.noContent().build();
    }
}
