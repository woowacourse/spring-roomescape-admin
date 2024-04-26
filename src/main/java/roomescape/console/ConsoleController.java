package roomescape.console;

import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@Controller
public class ConsoleController {
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController(ReservationService reservationService,
                             ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        try {
            Command command = InputView.inputCommand();
            while (command != Command.EXIT) {
                executeCommand(command);
                command = InputView.inputCommand();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            run();
        }
    }

    private void executeCommand(Command command) {
        switch (command) {
            case RESERVATION_GET -> printAllReservations();
            case RESERVATION_POST -> createReservation();
            case RESERVATION_DELETE -> deleteReservation();
            case RESERVATION_TIME_GET -> printAllReservationTimes();
            case RESERVATION_TIME_POST -> createReservationTime();
            case RESERVATION_TIME_DELETE -> deleteReservationTime();
        }
    }

    private void printAllReservations() {
        OutputView.printAllReservations(reservationService.findAll());
    }

    private void createReservation() {
        ReservationRequest reservationRequest = InputView.inputReservationRequest();
        ReservationResponse response = reservationService.save(reservationRequest);
        OutputView.printReservationResponse(response);
    }

    private void deleteReservation() {
        long id = InputView.inputDeleteId();
        boolean deleted = reservationService.deleteById(id);
        OutputView.printDeleted(deleted);
    }

    private void printAllReservationTimes() {
        OutputView.printAllReservationTimes(reservationTimeService.findAll());
    }

    private void createReservationTime() {
        ReservationTimeRequest reservationTimeRequest = InputView.inputReservationTimeRequest();
        ReservationTimeResponse response = reservationTimeService.save(reservationTimeRequest);
        OutputView.printReservationTimeResponse(response);
    }

    private void deleteReservationTime() {
        long id = InputView.inputDeleteId();
        boolean deleted = reservationTimeService.deleteById(id);
        OutputView.printDeleted(deleted);
    }
}
