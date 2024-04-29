package roomescape.console.presentation;

import java.util.List;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.core.service.ReservationService;
import roomescape.core.service.dto.ReservationRequest;
import roomescape.core.service.dto.ReservationResponse;

public class ReservationConsoleController {
    private final ReservationService reservationService;
    private final OutputView outputView;
    private final InputView inputView;

    public ReservationConsoleController(
            ReservationService reservationService, OutputView outputView, InputView inputView) {
        this.reservationService = reservationService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void findAllReservation() {
        List<ReservationResponse> reservations = reservationService.findAllReservation();
        outputView.printAllReservation(reservations);
    }

    public void saveReservation() {
        ReservationRequest content = inputView.askReservationContentToSave();
        ReservationResponse reservation = reservationService.saveReservation(content);
        outputView.printReservation(reservation);
    }

    public void deleteReservation() {
        Long id = inputView.askReservationIdToDelete();
        reservationService.deleteReservation(id);
    }
}
