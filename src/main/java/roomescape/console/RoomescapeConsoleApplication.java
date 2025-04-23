package roomescape.console;

import java.util.Scanner;
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("예약 시스템에 오신 것을 환영합니다!");
        while (true) {
            View.printMenu();
            Command choiceCommand = Command.from(scanner.nextInt());
            switch (choiceCommand) {
                case ADD_RESERVATION:
                    consoleReservationController.reserve();
                    break;
                case RESERVATIONS:
                    consoleReservationController.displayReservations();
                    break;
                case CANCEL_RESERVATION:
                    consoleReservationController.cancel();
                    break;
                case ADD_RESERVATION_TIME:
                    consoleReservationTimeController.addReservationTime();
                    break;
                case RESERVATION_TIMES:
                    consoleReservationTimeController.showReservationTimes();
                    break;
                case CANCEL_RESERVATION_TIME:
                    consoleReservationTimeController.deleteReservationTime();
                    break;
                case EXIT:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
                    break;
            }
        }
    }
}
