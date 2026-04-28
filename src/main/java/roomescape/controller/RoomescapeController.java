package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.request.ReservationRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class RoomescapeController {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0L);

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream().map(ReservationResponse::from).toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(ReservationRequest request) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation() {
        return null;
    }
}
