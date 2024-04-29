package roomescape.controller.console.command;

import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public interface Command {
    void execute(ReservationService reservationService,
                 ReservationTimeService reservationTimeService,
                 InputView inputView,
                 OutputView outputView);

    default boolean isEnd() {
        return false;
    }

    ;
}
