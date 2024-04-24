package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;

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
        Reservation requestReservation = reservationRequest.toReservation();
        Reservation responseReservation = reservationRepository.createReservation(requestReservation);

        ReservationResponse reservationResponse = ReservationResponse.from(responseReservation);

        return ResponseEntity.created(URI.create("/reservations/" + reservationResponse.getId()))
                .body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }
}
