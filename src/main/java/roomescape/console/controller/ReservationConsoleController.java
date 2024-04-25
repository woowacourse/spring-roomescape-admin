package roomescape.console.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.console.util.InputConverter;
import roomescape.console.view.Command;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservation.service.ReservationService;

@Controller
public class ReservationConsoleController implements ConsoleController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ReservationService reservationService;

    public ReservationConsoleController(InputView inputView, OutputView outputView, ReservationService reservationService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reservationService = reservationService;
    }

    public void postReservation(ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
        outputView.outputPostReservation(reservationResponse);
    }

    public void getReservations() {
        List<ReservationResponse> reservationResponses = reservationService.findAllReservations();
        outputView.outputGetReservations(reservationResponses);
    }

    public void deleteReservation(Long id) {
        reservationService.deleteReservation(id);
    }


    @Override
    public void run() {
        outputView.outputReservationMenu();
        List<String> strings = inputView.inputCommandLine();
        Command command = new Command(strings);
        switch (command.getCommandType()) {
            case POST:
                postReservation(InputConverter.toReservationRequest(command.getBody()));
                break;
            case GET:
                getReservations();
                break;
            case DELETE:
                deleteReservation(InputConverter.toId(command.getBody()));
                break;
            default:
                throw new IllegalArgumentException("잘못된 명령어입니다.");
        }
    }
}
