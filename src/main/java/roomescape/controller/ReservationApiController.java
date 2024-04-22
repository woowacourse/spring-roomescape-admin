package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationRequest;
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
        return new ReservationsResponse(reservationService.getReservations());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReservationCreateResponse createReservation(@RequestBody final ReservationRequest reservationRequest,
                                                       final HttpServletResponse response) {
        final ReservationCreateResponse reservationCreateResponse = reservationService.createReservation(reservationRequest);
        response.setHeader("Location", reservationCreateResponse.getLocationHeaderValue());
        return reservationCreateResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable final Long id) {
        reservationService.deleteReservation(id);
    }
}
