package roomescape.consoleview.command;

import java.util.List;
import roomescape.consoleview.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;

public class ShowCommand implements Command {

    private static final String RESERVATION = "reservation";
    private static final String TIME = "time";
    private static final int SHOW_ARGS = 1;

    @Override
    public void execute(
        List<String> arguments,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController,
        OutputView outputView) {

        validateArguments(arguments);

        if (arguments.get(0).equals(RESERVATION)) {
            outputView.printReservations(reservationController.findAll());
        }
        if (arguments.get(0).equals(TIME)) {
            outputView.printTimes(reservationTimeController.findAll());
        }
    }

    private void validateArguments(List<String> arguments) {
        if (arguments.size() != SHOW_ARGS) {
            throw new IllegalArgumentException("잘못된 명령어입니다.\n예) show reservation\n예) show time");
        }
    }
}
