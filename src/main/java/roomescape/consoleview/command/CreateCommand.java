package roomescape.consoleview.command;

import java.util.List;
import roomescape.consoleview.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.SaveReservationRequest;
import roomescape.controller.dto.SaveReservationResponse;
import roomescape.controller.dto.SaveReservationTimeRequest;
import roomescape.controller.dto.SaveReservationTimeResponse;

public class CreateCommand implements Command {

    private static final String RESERVATION = "reservation";
    private static final String TIME = "time";
    private static final int CREATE_RESERVATION_ARGS = 4;
    private static final int CREATE_TIME_ARGS = 2;

    private final OutputView outputView;

    public CreateCommand() {
        this.outputView = OutputView.getInstance();
    }

    @Override
    public void execute(
        List<String> arguments,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController) {

        validateArguments(arguments);

        if (arguments.get(0).equals(RESERVATION)) {
            String name = arguments.get(1);
            String date = arguments.get(2);
            Long timeId = Long.parseLong(arguments.get(3));
            SaveReservationResponse response = reservationController.save(
                new SaveReservationRequest(date, name, timeId));
            outputView.printSaveReservation(response);
        }

        if (arguments.get(0).equals(TIME)) {
            String startAt = arguments.get(1);
            SaveReservationTimeResponse response = reservationTimeController.save(
                new SaveReservationTimeRequest(startAt));
            outputView.printSaveTime(response);
        }
    }

    private void validateArguments(List<String> arguments) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException(
                "잘못된 명령어입니다.\n예) create reservation John 2025-01-01 1\n예) create time 23:00");
        }

        if (arguments.get(0).equals(RESERVATION) && arguments.size() != CREATE_RESERVATION_ARGS) {
            throw new IllegalArgumentException("잘못된 명령어입니다. 예) create reservation John 2025-01-01 1");
        }

        if (arguments.get(0).equals(TIME) && arguments.size() != CREATE_TIME_ARGS) {
            throw new IllegalArgumentException("잘못된 명령어입니다. 예) 예) create time 23:00");
        }
    }
}
