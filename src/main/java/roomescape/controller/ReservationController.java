package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.entity.Reservations;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations;
    private AtomicLong index = new AtomicLong(1);

    @Autowired
    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping()
    public List<Reservation> getReservations(
    ) {
        return reservations.findAllReservations();
    }

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(
            String name,
            LocalDate date,
            LocalTime time
    ) {
        Reservation reservation = new Reservation(index.getAndIncrement(), name, date, time);

        reservations.validateSameDateTime(reservation);
        reservations.saveReservation(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservations> deleteReservation(
            @PathVariable Long id
    ) {
        Reservation reservation = reservations.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.deleteReservation(reservation);

        return ResponseEntity.ok().body(reservations);
    }

}
