package roomescape.controller.console.command.reservationcommand;

import roomescape.controller.console.command.Command;
import roomescape.domain.reservation.Reservation;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.InputView;
import roomescape.view.OutputView;

import java.util.List;

public class DeleteReservation implements Command {
    @Override
    public void execute(ReservationService reservationService, ReservationTimeService reservationTimeService, InputView inputView, OutputView outputView) {
        //TODO Repository에게 시키기
        List<Reservation> reservations = reservationService.getAllReservations();

        if (reservations.isEmpty()) {
            outputView.printNoTimeMessage();
            return;
        }

        outputView.printAllReservation(reservationService.getAllReservations());
        reservationService.deleteReservation(inputView.readId());
    }
}
