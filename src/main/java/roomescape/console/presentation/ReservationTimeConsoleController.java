package roomescape.console.presentation;

import java.util.List;
import roomescape.console.view.InputView;
import roomescape.console.view.OutputView;
import roomescape.core.service.ReservationTimeService;
import roomescape.core.service.dto.ReservationTimeRequest;
import roomescape.core.service.dto.ReservationTimeResponse;

public class ReservationTimeConsoleController {
    private final ReservationTimeService reservationTimeService;
    private final OutputView outputView;
    private final InputView inputView;

    public ReservationTimeConsoleController(
            ReservationTimeService reservationTimeService, OutputView outputView, InputView inputView) {
        this.reservationTimeService = reservationTimeService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void findAllReservationTime() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAllReservationTime();
        outputView.printAllReservationTime(reservationTimes);
    }

    public void saveReservationTime() {
        ReservationTimeRequest content = inputView.askReservationTimeContentToSave();
        ReservationTimeResponse reservationTime = reservationTimeService.saveReservationTime(content);
        outputView.printReservationTime(reservationTime);
    }

    public void deleteReservationTime() {
        Long id = inputView.askReservationTimeIdToDelete();
        reservationTimeService.deleteReservationTime(id);
    }
}
