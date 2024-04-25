package roomescape.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;
import roomescape.view.ConsoleView;

@Component
public abstract class ReservationCommand extends Command{

    protected final ReservationService reservationService;
    protected final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationCommand(ConsoleView consoleView, ReservationService reservationService, ReservationTimeService reservationTimeService) {
        super(consoleView);
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }
}
