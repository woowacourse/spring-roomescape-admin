package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationRepository.createReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> read() {
        List<Reservation> reservations = reservationRepository.readReservations();
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationRepository.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
