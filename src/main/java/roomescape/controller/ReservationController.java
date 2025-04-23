package roomescape.controller;

import jakarta.validation.Valid;
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
import org.springframework.web.server.ResponseStatusException;
import roomescape.repository.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        final List<ReservationResponse> dtos = ReservationResponse.from(reservations);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody final ReservationRequest reservationRequest) {
        final Reservation reservation = makeReservation(reservationRequest);
        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable final Long id) {
        reservationRepository.delete(id);
    }

    private Reservation makeReservation(final ReservationRequest reservationRequest) {
        try {
            return reservationRepository.insert(reservationRequest.name(), reservationRequest.date(),
                    reservationRequest.time());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
