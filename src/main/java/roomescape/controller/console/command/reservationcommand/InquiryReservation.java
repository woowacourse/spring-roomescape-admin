package roomescape.controller.console.command.reservationcommand;

import roomescape.controller.console.command.Command;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

public class InquiryReservation implements Command {
    @Override
    public void execute(ReservationService reservationService,
                        ReservationTimeService reservationTimeService,
                        InputView inputView,
                        OutputView outputView) {
        outputView.printAllReservation(reservationService.getAllReservations());
    }
}
