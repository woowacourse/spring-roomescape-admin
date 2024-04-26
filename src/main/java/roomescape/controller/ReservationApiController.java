package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationsResponse;
import roomescape.service.ReservationService;


@RestController
@RequestMapping("reservations")
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ReservationsResponse reservations() {
        return reservationService.getReservations();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @Valid @RequestBody final ReservationCreateRequest reservationCreateRequest) {
        final ReservationCreateResponse reservationCreateResponse =
                reservationService.createReservation(reservationCreateRequest.convert());

        return ResponseEntity.created(reservationCreateResponse.getLocationHeaderValue())
                             .body(reservationCreateResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable final Long id) {
        reservationService.deleteReservation(id);
    }
}
