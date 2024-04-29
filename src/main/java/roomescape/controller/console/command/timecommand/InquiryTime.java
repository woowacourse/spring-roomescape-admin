package roomescape.controller.console.command.timecommand;

import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class InquiryTime extends TimeCommand {

    @Override
    void timeCommandExecute(ReservationTimeService reservationTimeService, InputView inputview, OutputView outputView) {
        outputView.printAllReservationTimes(reservationTimeService.allReservationTimes());
    }
}
