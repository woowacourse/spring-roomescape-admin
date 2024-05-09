package roomescape.consoleview.command;

import java.util.List;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;

@FunctionalInterface
public interface Command {

    void execute(
        List<String> arguments,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController
    );
}
