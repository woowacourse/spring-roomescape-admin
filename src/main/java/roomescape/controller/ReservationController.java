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
import roomescape.controller.request.CreateReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) {
        Long reservationId = reservationRepository.create(
                new Reservation(
                        createReservationRequest.name(),
                        createReservationRequest.date(),
                        createReservationRequest.time()));
        Reservation newReservation = reservationRepository.findById(reservationId).orElseThrow();
        return ResponseEntity.ok(new ReservationResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        ));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationRepository.deleteById(reservationId);
        return ResponseEntity.ok().build();
    }
}
