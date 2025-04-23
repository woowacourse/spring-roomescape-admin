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
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTImeRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTImeRepository reservationTImeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTImeRepository reservationTImeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTImeRepository = reservationTImeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        new ReservationTimeResponse(
                                reservation.getTime().id(),
                                reservation.getTime().startAt()
                        )
                ))
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTImeRepository.findById(createReservationRequest.timeId())
                .orElseThrow();
        Long reservationId = reservationRepository.create(
                new Reservation(
                        createReservationRequest.name(),
                        createReservationRequest.date(),
                        reservationTime));
        Reservation newReservation = reservationRepository.findById(reservationId).orElseThrow();
        return ResponseEntity.ok(new ReservationResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                new ReservationTimeResponse(
                        reservationTime.id(),
                        reservationTime.startAt()
                )
        ));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationRepository.deleteById(reservationId);
        return ResponseEntity.ok().build();
    }
}
