package roomescape.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.request.ReservationCreateRequest;
import java.util.ArrayList;
import roomescape.dto.response.ReservationResponse;

@Controller
public class ReservationController {

    private final AtomicLong id = new AtomicLong(0);
    private final Reservations reservations = new Reservations(new ArrayList<>());

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.getReservations()
                .stream()
                .map(ReservationResponse::fromReservation).toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservations(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = reservationCreateRequest.toReservation(id.incrementAndGet());
        reservations.add(reservation);

        ReservationResponse reservationResponse = ReservationResponse.fromReservation(reservation);
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservations.remove(id);
        return ResponseEntity.ok().build();
    }
}
