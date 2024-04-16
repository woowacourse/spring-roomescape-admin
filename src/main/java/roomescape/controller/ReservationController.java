package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @ResponseBody
    @GetMapping("")
    public List<ReservationResponse> findAllReservations() {
        List<ReservationResponse> responses = new ArrayList<>();
        for (final Reservation reservation : reservations) {
            responses.add(new ReservationResponse(reservation));
        }
        return responses;
    }

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
        Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).build();
    }
}
