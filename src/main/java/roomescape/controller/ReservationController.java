package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.infra.ReservationDatabase;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationDatabase reservationDatabase;

    public ReservationController(final ReservationDatabase reservationDatabase) {
        this.reservationDatabase = reservationDatabase;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getAll() {
        final List<Reservation> reservations = reservationDatabase.findAll();

        List<ReservationResponse> response = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> add(@RequestBody ReservationCreateRequest request) {
        final Long savedId = reservationDatabase.saveAndGetId(request);

        ReservationResponse response = ReservationResponse.from(request, savedId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationId") Long reservationId) {
        reservationDatabase.deleteById(reservationId);

        return ResponseEntity.ok().build();
    }
}
