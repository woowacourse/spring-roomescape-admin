package roomescape.controller.console.command;

import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class End implements Command {
    @Override
    public void execute(ReservationService reservationService, ReservationTimeService reservationTimeService, InputView inputView, OutputView outputView) {
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
