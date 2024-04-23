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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationCreateResponse;
import roomescape.controller.dto.ReservationFindResponse;
import roomescape.domain.Reservation;
import roomescape.repository.Reservations;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations;

    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ReservationFindResponse> getReservation() {
        return reservations.findReservations().stream()
                .map(ReservationFindResponse::of)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @RequestBody ReservationCreateRequest reservationCreateRequest) {

        Reservation createdReservation = reservations.createReservation(reservationCreateRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Location", "/reservations/" + createdReservation.getId())
                .body(ReservationCreateResponse.of(createdReservation));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id) {
        reservations.deleteReservationById(id);
    }
}
