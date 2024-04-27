package roomescape.controller.console.command.timecommand;

import roomescape.controller.console.command.Command;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public abstract class TimeCommand implements Command {
    @Override
    public void execute(ReservationService reservationService,
                        ReservationTimeService reservationTimeService,
                        InputView inputView,
                        OutputView outputView) {
        timeCommandExecute(reservationTimeService, inputView, outputView);
    }

    abstract void timeCommandExecute(ReservationTimeService reservationTimeService,
                                     InputView inputview,
                                     OutputView outputView);
}
