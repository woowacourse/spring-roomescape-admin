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
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> getReservations() {
        return reservationService.findAll();
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@RequestBody final ReservationRequest reservationRequest) {
        final ReservationResponse reservationResponse = reservationService.save(reservationRequest);
        final URI uri = UriComponentsBuilder.fromPath("/reservations/{id}")
                .buildAndExpand(reservationResponse.id())
                .toUri();
        return ResponseEntity.created(uri)
                .body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final long id) {
        try {
            reservationService.remove(id);
            return ResponseEntity.noContent()
                    .build();
        } catch (final IllegalArgumentException exception) {
            return ResponseEntity.notFound()
                    .build();
        }
    }
}
