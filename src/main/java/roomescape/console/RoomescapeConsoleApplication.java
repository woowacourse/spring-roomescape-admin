package roomescape.console;

import roomescape.console.controller.ConsoleReservationController;
import roomescape.console.controller.ConsoleReservationTimeController;
import roomescape.console.repository.MemoryReservationRepository;
import roomescape.console.repository.MemoryReservationTimeRepository;
import roomescape.console.view.Command;
import roomescape.console.view.View;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class RoomescapeConsoleApplication {

    private final ConsoleReservationController consoleReservationController;
    private final ConsoleReservationTimeController consoleReservationTimeController;

    public RoomescapeConsoleApplication() {
        var reservationRepository = new MemoryReservationRepository();
        var reservationTimeRepository = new MemoryReservationTimeRepository();
        var reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
        var reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        consoleReservationController = new ConsoleReservationController(reservationService, reservationTimeService);
        consoleReservationTimeController = new ConsoleReservationTimeController(reservationTimeService);
    }

    public static void main(String[] args) {
        var application = new RoomescapeConsoleApplication();
        application.run();
    }

    public void run() {
        while (true) {
            View.printWelcomeMessage();
            View.printCommands();
            Command command = getCommand();
            try {
                switch (command) {
                    case ADD_RESERVATION -> consoleReservationController.reserve();
                    case RESERVATIONS -> consoleReservationController.displayReservations();
                    case CANCEL_RESERVATION -> consoleReservationController.cancel();
                    case ADD_RESERVATION_TIME -> consoleReservationTimeController.addReservationTime();
                    case RESERVATION_TIMES -> consoleReservationTimeController.showReservationTimes();
                    case CANCEL_RESERVATION_TIME -> consoleReservationTimeController.deleteReservationTime();
                    case EXIT -> {
                        View.printGoodbyeMessage();
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                View.printErrorMessage(e.getMessage());
            }
        }
    }

    private static Command getCommand() {
        while (true) {
            try {
                return View.readCommand();
            } catch (IllegalArgumentException e) {
                View.printErrorMessage(e.getMessage());
            }
        }
    }
}
