package roomescape.console.controller;

import org.springframework.stereotype.Component;
import roomescape.console.view.ConsoleCommand;

@Component
public class DispatcherConsole {

    private final ConsoleReservationController reservationController;
    private final ConsoleReservationTimeController reservationTimeController;
    private final ConsoleInputConverter consoleInputConverter;

    public DispatcherConsole(
            ConsoleReservationController reservationController,
            ConsoleReservationTimeController reservationTimeController,
            ConsoleInputConverter consoleInputConverter
    ) {
        this.reservationController = reservationController;
        this.reservationTimeController = reservationTimeController;
        this.consoleInputConverter = consoleInputConverter;
    }

    public void doDispatch(ConsoleCommand command) {
        switch (command.consoleCommandType()) {
            case FIND_RESERVATIONS -> reservationController.findAll();
            case CREATE_RESERVATION -> reservationController.create(consoleInputConverter.toReservationServiceRequest(command.body()));
            case DELETE_RESERVATION -> reservationController.delete(consoleInputConverter.toLong(command.body().get(0)));
            case FIND_RESERVATION_TIMES -> reservationTimeController.findAll();
            case CREATE_RESERVATION_TIME -> reservationTimeController.create(consoleInputConverter.toReservationTimeServiceRequest(command.body()));
            case DELETE_RESERVATION_TIME -> reservationTimeController.delete(consoleInputConverter.toLong(command.body().get(0)));
        }
    }
}
