package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;
import roomescape.model.Reservations;

@RestController
public class ReservationController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(reservations.getReservations());
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationDto reservationDto) {
        try {
            ReservationDateTime reservationDateTime = new ReservationDateTime(
                    LocalDateTime.of(reservationDto.date(), reservationDto.time())
            );
            Reservation newReservation = new Reservation(
                    index.getAndIncrement(),
                    reservationDto.name(),
                    reservationDateTime);
            reservations.add(newReservation);
            return ResponseEntity.ok(newReservation);
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservations.removeById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
