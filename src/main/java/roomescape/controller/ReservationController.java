package roomescape.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final Service service;

    public ReservationController(ReservationRepository reservationRepository, Service service) {
        this.reservationRepository = reservationRepository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> reservations() {
        System.out.println("service.getClass() = " + service.getClass());
        return ResponseEntity.ok(reservationRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid Reservation reservation) {
        return ResponseEntity.ok(reservationRepository.save(reservation));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationRepository.remove(reservationId);
        return ResponseEntity.ok().build();
    }
}
