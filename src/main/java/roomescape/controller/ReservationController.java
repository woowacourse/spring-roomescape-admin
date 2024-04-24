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
        Reservation newReservation = new Reservation(null, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());

        Reservation reservation = reservationRepository.insert(newReservation);
        return ResponseEntity.ok(new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime()));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readReservations() {
        return reservationRepository.list().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
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
