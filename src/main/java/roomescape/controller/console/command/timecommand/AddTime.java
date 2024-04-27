package roomescape.controller.console.command.timecommand;

import roomescape.domain.reservation.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class AddTime extends TimeCommand {
    @Override
    public void timeCommandExecute(ReservationTimeService reservationTimeService,
                                   InputView inputView,
                                   OutputView outputView) {
        ReservationTime reservationTime = new ReservationTime(inputView.readTime());
        reservationTimeService.addTime(reservationTime);
    }
}
