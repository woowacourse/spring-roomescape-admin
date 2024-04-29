package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationsController(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<Reservation> readReservations() {
        return reservationService.readReservations();
    }

    @PostMapping
    public Reservation create(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        return reservationService.create(reservationCreateRequest, reservationTimeService.findById(reservationCreateRequest));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationService.delete(id);
    }
}
