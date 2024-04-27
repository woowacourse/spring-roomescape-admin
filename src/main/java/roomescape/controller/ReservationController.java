package roomescape.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationCreateResponse;
import roomescape.controller.dto.ReservationFindResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationFindResponse>> getReservation() {
        List<ReservationFindResponse> reservationResponses = reservationRepository.findReservations().stream()
                .map(ReservationFindResponse::of)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @RequestBody ReservationCreateRequest reservationCreateRequest) {

        Optional<ReservationTime> reservationTime = timeRepository.findReservationTimeById(
                reservationCreateRequest.timeId());
        if (reservationTime.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Reservation requestedReservation = reservationCreateRequest.toReservationWith(reservationTime.get());

        Reservation createdReservation = reservationRepository.createReservation(requestedReservation);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", "/reservations/" + createdReservation.getId())
                .body(ReservationCreateResponse.of(createdReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteReservationById(id);

        return ResponseEntity.noContent().build();
    }
}
