package roomescape.consoleview.command;

import java.util.List;
import roomescape.consoleview.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.controller.dto.SaveReservationRequest;
import roomescape.controller.dto.SaveReservationTimeRequest;

public class CreateCommand implements Command {

    private static final String RESERVATION = "reservation";
    private static final String TIME = "time";
    private static final int CREATE_RESERVATION_ARGS = 4;
    private static final int CREATE_TIME_ARGS = 2;

    @Override
    public void execute(
        List<String> arguments,
        ReservationController reservationController,
        ReservationTimeController reservationTimeController,
        OutputView outputView) {

        validateArguments(arguments);

        if (arguments.get(0).equals(RESERVATION)) {
            String name = arguments.get(1);
            String date = arguments.get(2);
            Long timeId = Long.parseLong(arguments.get(3));
            reservationController.save(new SaveReservationRequest(date, name, timeId));
        }

        if (arguments.get(0).equals(TIME)) {
            String startAt = arguments.get(1);
            reservationTimeController.save(new SaveReservationTimeRequest(startAt));
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
