package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        List<Reservation> reservations = reservationService.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequestDto request) {
        Reservation reservation = reservationService.create(request);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
