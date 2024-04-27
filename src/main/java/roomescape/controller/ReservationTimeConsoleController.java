package roomescape.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimesResponse;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleInputView;
import roomescape.view.ConsoleOutputView;

@Controller
@Profile("console")
public class ReservationTimeConsoleController {
    private final ReservationTimeService reservationTimeService;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public ReservationTimeConsoleController(final ReservationTimeService reservationTimeService, final ConsoleInputView consoleInputView, final ConsoleOutputView consoleOutputView) {
        this.reservationTimeService = reservationTimeService;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    public void printReservationTimes() {
        final ReservationTimesResponse response = this.reservationTimeService.getReservationTimes();
        consoleOutputView.printReservationTimes(response);
    }

    public void createReservation() {
        final String time = consoleInputView.inputTime();

        final ReservationTimeCreateResponse reservationCreateResponse =
                reservationTimeService.createReservationTime(time);

        consoleOutputView.printReservationTimeCreate(reservationCreateResponse);
    }

    public void cancelReservation() {
        final long id = consoleInputView.inputId();

        reservationTimeService.deleteReservationTime(id);
    }
}
