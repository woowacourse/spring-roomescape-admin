package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations() {
        return ResponseEntity.ok(service.readReservations());
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + service.createReservation(dto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
