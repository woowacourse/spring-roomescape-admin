package roomescape.reservation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.RequestReservation;
import roomescape.reservation.dto.ResponseReservation;

@RestController
public class ReservationApiController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong();

    @GetMapping("/reservations")
    public ResponseEntity<List<ResponseReservation>> findAll() {
        List<ResponseReservation> responseReservations = reservations.stream()
                .map(reservation -> new ResponseReservation(reservation.getId(), reservation.getName(),
                        reservation.getDate(), reservation.getTime()))
                .toList();

        return ResponseEntity.ok(responseReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ResponseReservation> create(@RequestBody RequestReservation requestReservation) {
        Reservation reservation = new Reservation(atomicLong.incrementAndGet(), requestReservation.name(),
                requestReservation.date(), requestReservation.time());
        reservations.add(reservation);

        return ResponseEntity.ok(
                new ResponseReservation(reservation.getId(), reservation.getName(), reservation.getDate(),
                        reservation.getTime()));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .ifPresent(reservations::remove);

        return ResponseEntity.ok().build();
    }
}
