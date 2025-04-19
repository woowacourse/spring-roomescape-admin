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
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong counter = new AtomicLong(1);
    private final Reservations reservations = new Reservations(new ArrayList<>());

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<ReservationResponse> dtos = ReservationResponse.from(reservations);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        LocalDateTime dateTime = LocalDateTime.of(reservationRequest.date(), reservationRequest.time());
        Reservation reservation = new Reservation(counter.getAndIncrement(), reservationRequest.name(), dateTime);
        reservations.add(reservation);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
