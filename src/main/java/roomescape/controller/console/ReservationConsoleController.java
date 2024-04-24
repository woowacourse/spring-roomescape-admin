package roomescape.controller.console;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import roomescape.RoomescapeConsoleApplication;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.service.ReservationService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

@Component
@ConditionalOnBean(RoomescapeConsoleApplication.class)
public class ReservationConsoleController extends ManagementController {

    private final ReservationService reservationService;

    public ReservationConsoleController(ReservationService reservationService) {
        super();
        this.reservationService = reservationService;
    }

    @Override
    protected void showAllResults() {
        List<ReservationResponse> responses = reservationService.getAllReservations();
        OutputView.printReservations(responses);
        OutputView.printReservationManagementMenu();
    }

    @Override
    public void create() {
        ReservationRequest request = InputView.createReservationRequest();
        ReservationResponse response = reservationService.scheduleReservation(request);
        OutputView.printReservationCreationResponse(response);
    }

    @Override
    public void delete() {
        long reservationId = InputView.inputDeleteReservationId();
        reservationService.cancelReservation(reservationId);
        OutputView.printDeleteReservationMessage();
    }
}
