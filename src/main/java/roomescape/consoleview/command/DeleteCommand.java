package roomescape.consoleview.command;

import java.util.List;
import roomescape.consoleview.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;

public class DeleteCommand implements Command {

    private static final String RESERVATION = "reservation";
    private static final String TIME = "time";
    private static final int DELETE_ARGS = 2;

    private final OutputView outputView;

    public DeleteCommand() {
        this.outputView = OutputView.getInstance();
    }

    @Override
    public void execute(
        List<String> arguments,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController) {

        validateArguments(arguments);

        if (arguments.get(0).equals(RESERVATION)) {
            long reservationId = Long.parseLong(arguments.get(1));
            reservationController.delete(reservationId);
        }
        if (arguments.get(0).equals(TIME)) {
            long timeId = Long.parseLong(arguments.get(1));
            reservationTimeController.delete(timeId);
        }
    }

    private void validateArguments(List<String> arguments) {
        if (arguments.size() != DELETE_ARGS) {
            throw new IllegalArgumentException("잘못된 명령어입니다.\n예) delete reservation 1\n예) delete time 1");
        }
    }
}
