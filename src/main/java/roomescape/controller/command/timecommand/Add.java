package roomescape.controller.command.timecommand;

import roomescape.domain.reservation.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class Add extends TimeCommand {
    @Override
    public void timeCommandExecute(ReservationTimeService reservationTimeService,
                                   InputView inputView,
                                   OutputView outputView) {
        ReservationTime reservationTime = new ReservationTime(inputView.readTime());
        reservationTimeService.addTime(reservationTime);
    }
}
