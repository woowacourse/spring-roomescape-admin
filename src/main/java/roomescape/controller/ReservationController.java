package roomescape.controller;

import java.net.URI;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservations;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations;

    public ReservationController() {
        this.reservations = new Reservations(new ArrayList<>());
    }

    @GetMapping
    public List<ReservationResponse> findAll() {
        return reservations.findAll().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest reservationCreateRequest) {

        final Long id = reservations.add(
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time(),
                Clock.systemDefaultZone()
        );
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.remove(id);
        return ResponseEntity.noContent().build();
    }
}
