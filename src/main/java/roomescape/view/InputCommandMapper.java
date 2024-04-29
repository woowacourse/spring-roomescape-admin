package roomescape.view;

import roomescape.controller.command.*;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class InputCommandMapper {

    // TODO: refactoring - parameter
    public static Command findCommand(String input, ConsoleView consoleView, ReservationService reservationService, ReservationTimeService reservationTimeService) {
        if (input.equals("1")) {
            return new TimeShowCommand(consoleView, reservationTimeService);
        }
        if (input.equals("2")) {
            return new TimeSaveCommand(consoleView, reservationTimeService);
        }
        if (input.equals("3")) {
            return new TimeDeleteCommand(consoleView, reservationTimeService);
        }
        if (input.equals("4")) {
            return new ReservationShowCommand(consoleView, reservationService);
        }
        if (input.equals("5")) {
            return new ReservationSaveCommand(consoleView, reservationService);
        }
        if (input.equals("6")) {
            return new ReservationDeleteCommand(consoleView, reservationService);
        }
        return new ExitCommand(consoleView);
    }
}