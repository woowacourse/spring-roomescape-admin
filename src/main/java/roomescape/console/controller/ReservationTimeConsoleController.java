package roomescape.console.controller;

import java.util.List;
import roomescape.console.util.InputConverter;
import roomescape.console.view.Command;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.web.dto.request.ReservationTimeRequest;
import roomescape.web.dto.response.ReservationTimeResponse;
import roomescape.core.service.ReservationTimeService;

public class ReservationTimeConsoleController implements ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeConsoleController(InputView inputView, OutputView outputView, ReservationTimeService reservationTimeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationTimeService = reservationTimeService;
    }

    public void postReservationTime(ReservationTimeRequest request) {
        ReservationTimeResponse reservationTime = reservationTimeService.createReservationTime(request);
        outputView.outputPostReservationTime(reservationTime);
    }

    public void getReservationTimes() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAllReservationTimes();
        outputView.outputGetReservationTimes(reservationTimes);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeService.deleteReservationTime(id);
        outputView.outputDeleteReservationTime(id);
    }

    @Override
    public void run() {
        List<String> strings = inputView.inputReservationTimeMenu();
        Command command = new Command(strings);
        switch (command.getCommandType()) {
            case POST -> postReservationTime(InputConverter.toReservationTimeRequest(command.getBody()));
            case GET -> getReservationTimes();
            case DELETE -> deleteReservationTime(InputConverter.toId(command.getBody()));
            default -> throw new IllegalArgumentException("잘못된 명령어입니다.");
        }
    }
}
