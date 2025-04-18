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
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAll() {
        return ResponseEntity.ok().body(createReservationResponses(reservationService.findAll()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest reservationCreateRequest) {

        final Long id = reservationService.create(
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time()
        );
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private List<ReservationResponse> createReservationResponses(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::createReservationResponse)
                .toList();
    }

    private ReservationResponse createReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
