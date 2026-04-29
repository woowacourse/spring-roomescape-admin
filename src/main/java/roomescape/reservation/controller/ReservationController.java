package roomescape.reservation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
