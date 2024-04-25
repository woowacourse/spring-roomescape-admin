package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> readReservations() {
        return reservationService.readReservations();
    }

    @GetMapping("/{id}")
    public Reservation readReservation(@PathVariable Long id) {
        return reservationService.readReservation(id);
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationCreateRequest request) {
        return reservationService.createReservation(request);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
