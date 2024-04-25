package roomescape.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.controller.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(null, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());

        Reservation savedReservation = reservationRepository.insert(reservation);
        return ResponseEntity.ok(new ReservationResponse(savedReservation.id(), savedReservation.name(), savedReservation.date(), savedReservation.time()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readReservations() {
        return reservationRepository.list().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.id(),
                        reservation.name(),
                        reservation.date(),
                        reservation.time()
                        )
                )
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.delete(id);

        return ResponseEntity.ok().build();
    }

}
