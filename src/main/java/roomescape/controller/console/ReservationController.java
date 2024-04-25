package roomescape.controller.console;

import org.springframework.stereotype.Controller;
import roomescape.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController() {
        this.reservationService = new ReservationService();
    }
}
