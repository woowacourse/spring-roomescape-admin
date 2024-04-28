package roomescape.controller.console.command.timecommand;

import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class DeleteTime extends TimeCommand {
    @Override
    public void timeCommandExecute(ReservationTimeService reservationTimeService,
                                   InputView inputView,
                                   OutputView outputView) {
        outputView.printAllReservationTimes(reservationTimeService.allReservationTimes());
        reservationTimeService.delete(inputView.readId());
    }

}
