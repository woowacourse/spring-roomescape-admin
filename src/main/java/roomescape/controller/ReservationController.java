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
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<Reservation> reservations = reservationRepository.findAllReservations();
        
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(reservation -> ReservationResponse.from(reservation))
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationRepository.createReservation(reservationRequest);

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean isDeleted = reservationRepository.deleteReservation(id);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
