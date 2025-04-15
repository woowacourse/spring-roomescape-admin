package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final Reservations reservations = new Reservations(new ArrayList<>());

    @GetMapping("/admin/reservation")
    public String showReservationManagementPage() {
        return "/admin/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<ReservationResponse> dtos = ReservationResponse.from(reservations);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        LocalDateTime dateTime = LocalDateTime.of(reservationRequest.date(), reservationRequest.time());
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationRequest.name(), dateTime);
        reservations.add(reservation);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
