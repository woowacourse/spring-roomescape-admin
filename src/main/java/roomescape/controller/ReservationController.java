package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;
import roomescape.controller.request.ReservationRequest;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationDto) {
        Reservation reservation = new Reservation(index.getAndIncrement(), reservationDto.getName(),
                reservationDto.getDate(), reservationDto.getTime());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") long id) {
        Optional<Reservation> reservationOptional = reservations.stream()
                .filter(it -> it.getId() == id)
                .findFirst();

        if (reservationOptional.isPresent()) {
            reservations.remove(reservationOptional.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
