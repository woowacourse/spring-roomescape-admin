package roomescape.controller.console.command.timecommand;

import roomescape.domain.reservation.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

import java.util.List;

public class InquiryTime extends TimeCommand {

    @Override
    void timeCommandExecute(ReservationTimeService reservationTimeService, InputView inputview, OutputView outputView) {
        List<ReservationTime> reservationTimes = reservationTimeService.allReservationTimes();

        if (reservationTimes.isEmpty()) {
            outputView.printNoTimeMessage();
            return;
        }
        outputView.printAllReservationTimes(reservationTimes);
    }
}
