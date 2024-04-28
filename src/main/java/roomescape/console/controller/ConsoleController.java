package roomescape.console.controller;

import roomescape.console.view.Command;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

public class ConsoleController {
    private final OutputView outputView;
    private final InputView inputView;
    private final ReservationTimeService reservationTimeService;

    public ConsoleController(OutputView outputView, InputView inputView,
                             ReservationTimeService reservationTimeService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        outputView.printStartMessage();
        outputView.printAdminFunction();

        Command command = inputView.readCommand();
        while (command != Command.END) {
            switch (command) {
                case READ_ALL_RESERVATION_TIMES -> getReservationTimes();
                case CREATE_RESERVATION_TIME -> createReservationTime();
                case DELETE_RESERVATION_TIME -> deleteReservationTime();
            }
            outputView.printAdminFunction();
            command = inputView.readCommand();
        }
    }

    private void getReservationTimes() {
        outputView.printAllReservationTimes(reservationTimeService.getAllReservationTimes());
    }

    private void createReservationTime() {
        ReservationTimeRequest request = inputView.readReservationTimeRequest();
        ReservationTimeResponse response = reservationTimeService.createReservationTime(request);
        outputView.printCreateTimeSuccessMessage(response);
    }

    private void deleteReservationTime() {
        Long id = inputView.readDeleteReservationTimeId();
        reservationTimeService.deleteReservationTime(id);
        outputView.printDeleteTimeSuccessMessage();
    }
}
