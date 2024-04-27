package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.service.ReservationService;
import roomescape.service.TimeManagementService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private final ReservationService reservationService;
    private final TimeManagementService timeManagementService;

    public ReservationsController(ReservationService reservationService, TimeManagementService timeManagementService) {
        this.reservationService = reservationService;
        this.timeManagementService = timeManagementService;
    }

    @GetMapping
    public List<Reservation> read() {
        return reservationService.read();
    }

    @PostMapping
    public Reservation create(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.create(reservationRequest, timeManagementService.findById(reservationRequest));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        reservationService.delete(id);
    }
}
