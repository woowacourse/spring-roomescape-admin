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
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @PostMapping("")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
        Reservation newReservation = Reservation.toEntity(index.getAndIncrement(), reservation);
        reservations.add(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
        reservations.remove(target);
        return ResponseEntity.ok().build();
    }
}
