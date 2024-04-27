package roomescape.controller.command.timecommand;

import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class Delete extends TimeCommand {
    @Override
    public void timeCommandExecute(ReservationTimeService reservationTimeService,
                                   InputView inputView,
                                   OutputView outputView) {
        reservationTimeService.delete(inputView.readId());
    }

}
