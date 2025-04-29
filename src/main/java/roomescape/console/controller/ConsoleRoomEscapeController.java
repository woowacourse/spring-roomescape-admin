package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.constant.MenuOption;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.service.ReservationService;

@Controller
public class ConsoleRoomEscapeController {
    private final ReservationService reservationService;
    private final OutputView outputView;
    private final InputView inputView;

    public ConsoleRoomEscapeController(ReservationService reservationService,
                                       OutputView outputView,
                                       InputView inputView) {
        this.reservationService = reservationService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        printReservationsStatus();
        MenuOption option = inputView.getMenuOption();
        while (option != MenuOption.EXIT) {
            if (option == MenuOption.RESERVATION_CREATE) {
                createReservation();
            }
            if (option == MenuOption.RESERVATION_DELETE) {
                deleteReservation();
            }
            if (option == MenuOption.RESERVATION_TIME_CREATE) {
                createReservationTime();
            }
            if (option == MenuOption.RESERVATION_TIME_DELETE) {
                deleteReservationTime();
            }
            printReservationsStatus();
            option = inputView.getMenuOption();
        }
    }

    private void deleteReservationTime() {
        Long deleteId = inputView.getDeleteReservationTimeId();
        reservationService.deleteReservationTime(deleteId);
    }

    private void createReservationTime() {
        ReservationTimeRequest request = inputView.getReservationTimeAddRequest();
        reservationService.createReservationTime(request);
    }

    private void deleteReservation() {
        Long deleteId = inputView.getDeleteReservationId();
        reservationService.deleteReservation(deleteId);
    }

    private void createReservation() {
        ReservationRequest request = inputView.getReservationAddRequest();
        reservationService.createReservation(request);
    }

    private void printReservationsStatus() {
        outputView.printReservationInfo(reservationService.findAllReservations(),
                reservationService.findAllReservationTimes());
    }
}
