package roomescape.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations = new Reservations();

    @GetMapping
    public List<ReservationResponse> readReservations() {
        final List<ReservationResponse> dtos = ReservationResponse.from(reservations);
        return dtos;
    }

    @PostMapping
    public ReservationResponse createReservation(
            @Valid @RequestBody final ReservationRequest reservationRequest) {
        final Reservation reservation = makeReservation(reservationRequest);
        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable final Long id) {
        reservations.deleteById(id);
    }

    private Reservation makeReservation(final ReservationRequest reservationRequest) {
        final LocalDateTime dateTime = LocalDateTime.of(reservationRequest.date(), reservationRequest.time());
        try {
            return reservations.addReservation(reservationRequest.name(), dateTime);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
