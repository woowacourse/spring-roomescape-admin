package roomescape.console.command;

import org.springframework.stereotype.Component;
import roomescape.controller.ReservationTimeApiController;

@Component
public class FindTimesCommand implements ConsoleCommand {

    private final ReservationTimeApiController reservationTimeApiController;

    public FindTimesCommand(ReservationTimeApiController reservationTimeApiController) {
        this.reservationTimeApiController = reservationTimeApiController;
    }

    @Override
    public void conduct() {
        System.out.println(reservationTimeApiController.getReservationTimes());
    }
}
