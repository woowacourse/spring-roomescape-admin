package roomescape.controller.console;

import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;
import roomescape.view.ReservationTimeView;

@Controller
public class ReservationTimeConsoleController {
    private final ReservationTimeView reservationTimeView;
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeConsoleController(final ReservationTimeService reservationTimeService) {
        reservationTimeView = new ReservationTimeView();
        this.reservationTimeService = reservationTimeService;
    }

    public void saveTime() {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(
                reservationTimeView.readStartAt()
        );
        reservationTimeService.saveTime(reservationTimeRequest);
        reservationTimeView.printSuccessfullyAdded();
    }

    public void getTimes() {
        reservationTimeView.printReservationTimes(reservationTimeService.getReservationTimes());
    }

    public void deleteTime() {
        try {
            List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findReservationTimes();
            reservationTimeService.deleteTime(
                    reservationTimeView.readIndexToDelete(reservationTimeResponses)
            );
            reservationTimeView.printSuccessfullyDeleted();
        } catch (IllegalStateException exception) {
            reservationTimeView.printHasNotAnyReservationTimeToDelete();
        }
    }
}
