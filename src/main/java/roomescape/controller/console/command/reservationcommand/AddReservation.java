package roomescape.controller.console.command.reservationcommand;

import roomescape.controller.console.command.Command;
import roomescape.controller.web.request.ReservationRequest;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

import java.time.LocalDate;

public class AddReservation implements Command {
    @Override
    public void execute(ReservationService reservationService,
                        ReservationTimeService reservationTimeService,
                        InputView inputView,
                        OutputView outputView) {
        reservationService.reserve(readReservation(inputView, outputView, reservationTimeService));
    }

    private ReservationRequest readReservation(InputView inputView, OutputView outputView, ReservationTimeService reservationTimeService) {
        String name = inputView.readName();
        LocalDate date = inputView.readDate();
        outputView.printAllReservationTimes(reservationTimeService.allReservationTimes());
        return new ReservationRequest(inputView.readName(), inputView.readDate(), inputView.readId());
    }
}
