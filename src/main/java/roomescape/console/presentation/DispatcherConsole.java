package roomescape.console.presentation;

import roomescape.console.view.Command;

public class DispatcherConsole {
    private final ReservationConsoleController reservationController;
    private final ReservationTimeConsoleController reservationTimeController;

    public DispatcherConsole(ReservationConsoleController reservationController,
                             ReservationTimeConsoleController reservationTimeController) {
        this.reservationController = reservationController;
        this.reservationTimeController = reservationTimeController;
    }

    public void doDispatch(Command command) {
        if (command == Command.FIND_ALL_RESERVATION) {
            reservationController.findAllReservation();
            return;
        }
        if (command == Command.SAVE_RESERVATION) {
            reservationController.saveReservation();
            return;
        }
        if (command == Command.DELETE_RESERVATION) {
            reservationController.deleteReservation();
            return;
        }
        if (command == Command.FIND_ALL_TIME) {
            reservationTimeController.findAllReservationTime();
            return;
        }
        if (command == Command.SAVE_TIME) {
            reservationTimeController.saveReservationTime();
            return;
        }
        if (command == Command.DELETE_TIME) {
            reservationTimeController.deleteReservationTime();
            return;
        }
        throw new IllegalArgumentException("지원하지 않는 기능입니다.");
    }
}
