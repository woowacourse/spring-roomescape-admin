package roomescape.control;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.ReservationService;
import roomescape.control.dto.CreateReservationRequest;
import roomescape.control.dto.ReservationResponse;

@RequestMapping("/reservations")
@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        final List<ReservationResponse> reservationResponses = reservationService.getAllReservations().stream()
                .map(ReservationResponse::of)
                .toList();
       return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
        final LocalDateTime reservationDateTime = LocalDateTime.parse(request.date() + "T" + request.time());
        final Long createdId = reservationService.createReservation(request.name(), reservationDateTime);

        return ResponseEntity.ok(ReservationResponse.of(reservationService.findReservationById(createdId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
