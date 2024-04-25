package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.controller.ReservationApiController;

@Component
public class FindReservationsCommand implements ConsoleCommand {

    private final ReservationApiController reservationApiController;

    public FindReservationsCommand(ReservationApiController reservationApiController) {
        this.reservationApiController = reservationApiController;
    }

    @Override
    public void conduct() {
        System.out.println(reservationApiController.getReservations());
    }
}
