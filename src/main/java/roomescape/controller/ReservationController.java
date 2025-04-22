package roomescape.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public List<Reservation> getReservations(
    ) {
        return reservationService.findAllReservations();
    }

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation
    ) {
        try{
            reservationService.validateReservationTimeAvailability(reservation);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Reservation newReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Reservation>> deleteReservation(
            @PathVariable Long id
    ) {
        Reservation reservation = reservationService.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservationService.deleteReservation(reservation.getId());

        return ResponseEntity.ok().body(reservationService.findAllReservations());
    }

}
