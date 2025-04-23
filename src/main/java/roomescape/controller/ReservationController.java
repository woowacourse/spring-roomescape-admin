package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.model.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.repository.ReservationRepository;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationRepository.getReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody CreateReservationRequest request) {
        try {
            long savedId = reservationRepository.save(request);
            Reservation saved = reservationRepository.findById(savedId).get();
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        boolean isRemoved = reservationRepository.removeById(id);
        if (isRemoved) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
