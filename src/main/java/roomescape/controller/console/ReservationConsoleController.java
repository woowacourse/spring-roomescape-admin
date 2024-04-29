package roomescape.controller.console;

import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;
import roomescape.view.command.ConsoleCommand;

@Controller
public class ReservationConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationConsoleController(InputView inputView, OutputView outputView,
                                        ReservationService reservationService,
                                        ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void start() {
        outputView.printCommands();
        int command;
        while (true) {
            command = inputView.askInputNumber();
            ConsoleCommand consoleCommand = ConsoleCommand.getConsoleCommand(command);
            switch (consoleCommand) {
                case FIND_RESERVATIONS -> printReservations();
                case FIND_RESERVATION_TIMES -> printReservationTimes();
                case ADD_RESERVATION -> addReservations();
                case ADD_RESERVATION_TIME -> addReservationTimes();
                case DELETE_RESERVATION -> deleteReservation();
                case DELETE_RESERVATION_TIME -> deleteReservationTime();
                case EXIT -> outputView.printEnd();
                default -> throw new RuntimeException();
            }
            if (consoleCommand.isEnd()) {
                return;
            }
        }

    }

    private void printReservations() {
        List<Reservation> allReservations = reservationService.findAllReservations();
        outputView.printAllReservations(allReservations);
    }

    private void printReservationTimes() {
        List<ReservationTime> allReservationTimes = reservationTimeService.findAllReservationTimes();
        outputView.printAllReservationTimes(allReservationTimes);
    }


    private void addReservations() {
        List<ReservationTime> allReservationTimes = reservationTimeService.findAllReservationTimes();
        outputView.printAllReservationTimes(allReservationTimes);
        ReservationRequest request = inputView.askInputReservation();
        reservationService.addReservation(request);
        outputView.printReservationComplete();
    }

    private void addReservationTimes() {
        ReservationTimeRequest request = inputView.askInputReservationTime();
        reservationTimeService.addReservationTime(request);
        outputView.printAddReservationTime();
    }

    private void deleteReservation() {
        long reservationId = inputView.askInputReservationId();
        reservationService.deleteReservation(reservationId);
        outputView.printReservationDelete();
    }

    private void deleteReservationTime() {
        long reservationTimeId = inputView.askInputReservationTimeId();
        reservationTimeService.deleteReservationTime(reservationTimeId);
        outputView.printReservationTimeDelete();
    }
}
