package roomescape.controller;

import java.util.List;
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
import roomescape.repository.ReservationRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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

        Reservation createdReservation = reservationRepository.createReservation(reservationCreateRequest);

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
