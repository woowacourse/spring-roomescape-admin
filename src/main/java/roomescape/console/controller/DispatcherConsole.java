package roomescape.console.controller;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.console.view.ConsoleCommand;
import roomescape.service.dto.ReservationServiceRequest;
import roomescape.service.dto.ReservationTimeServiceRequest;

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
            case CREATE_RESERVATION -> createReservation(command.body());
            case DELETE_RESERVATION -> deleteReservation(command.body());
            case FIND_RESERVATION_TIMES -> reservationTimeController.findAll();
            case CREATE_RESERVATION_TIME -> createReservationTime(command.body());
            case DELETE_RESERVATION_TIME -> deleteReservationTime(command.body());
        }
    }

    private void createReservation(List<String> body) {
        ReservationServiceRequest request = consoleInputConverter.toReservationServiceRequest(body);
        reservationController.create(request);
    }

    private void deleteReservation(List<String> body) {
        Long id = consoleInputConverter.toId(body);
        reservationController.delete(id);
    }

    private void createReservationTime(List<String> body) {
        ReservationTimeServiceRequest request = consoleInputConverter.toReservationTimeServiceRequest(body);
        reservationTimeController.create(request);
    }

    private void deleteReservationTime(List<String> body) {
        Long id = consoleInputConverter.toId(body);
        reservationTimeController.delete(id);
    }
}
