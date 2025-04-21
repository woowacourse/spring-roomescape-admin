package roomescape.console.controller;

import java.util.List;
import roomescape.console.view.ConsoleInputView;
import roomescape.console.view.ConsoleOutputView;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

public class ConsoleController {
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    private final ConsoleInputView inputView = new ConsoleInputView();
    private final ConsoleOutputView outputView = new ConsoleOutputView();

    public ConsoleController(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    public void run() {
        createReservationTime();
        createReservation();
    }

    private void createReservationTime() {
        outputView.printCreateTimeTitle();
        do {
            ReservationTimeCreateRequest request = inputView.readTime();
            reservationTimeService.save(request);
        } while (inputView.readContinue());
    }

    private void createReservation() {
        outputView.printCreateReservationTitle();
        do {
            List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();
            outputView.printTimes(reservationTimes);

            ReservationCreateRequest request = inputView.readCreateReservation();
            ReservationResponse saved = reservationService.save(request);

            outputView.printReservation(saved);
        } while (inputView.readContinue());
    }
}
