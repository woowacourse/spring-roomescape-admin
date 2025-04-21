package roomescape.controller;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private AtomicLong index = new AtomicLong(1);

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping()
    public List<Reservation> getReservations(
    ) {
        return reservationRepository.findAllReservations();
    }

    @PostMapping()
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservation
    ) {
        Reservation newReservation = Reservation.toEntity(reservation, index.getAndIncrement());

        /*try{
            reservationRepository.validateReservationTimeAvailability(newReservation);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }*/

        reservationRepository.saveReservation(newReservation);
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Reservation>> deleteReservation(
            @PathVariable Long id
    ) {
        Reservation reservation = reservationRepository.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservationRepository.deleteReservation(reservation.getId());

        return ResponseEntity.ok().body(reservationRepository.findAllReservations());
    }

}
