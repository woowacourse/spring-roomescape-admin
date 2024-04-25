package roomescape.controller.console;

import roomescape.service.ReservationService;

public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController() {
        this.reservationService = new ReservationService();
    }
}
